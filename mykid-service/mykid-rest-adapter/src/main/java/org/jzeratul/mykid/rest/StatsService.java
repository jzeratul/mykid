package org.jzeratul.mykid.rest;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jzeratul.mykid.model.DailyTotals;
import org.jzeratul.mykid.model.GenericActivities;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.Stats;
import org.jzeratul.mykid.model.UserRecord;
import org.jzeratul.mykid.storage.StatsDataStore;
import org.jzeratul.mykid.storage.UserDataStore;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

  protected static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MMM-YYYY HH:mm");

  private final CurrentUserService userService;
  private final StatsDataStore statsStore;
  private final UserDataStore userStore;

  public StatsService(StatsDataStore statsStore, UserDataStore userStore, CurrentUserService userService) {
    this.userService = userService;
    this.statsStore = statsStore;
    this.userStore = userStore;
  }

  public void storeStats(Stats stats) {
    statsStore.storeStats(mapFromStats(stats));
  }

  public GetStatsResponse getStats(OffsetDateTime start, OffsetDateTime end) {

    Map<String, List<KidStatsRecord>> dailySortedStats = statsStore.getStats(start, end, userService.getCurrentUserId())
            .stream()
            .collect(
                    Collectors.groupingBy(
                            s -> s.datetime().getYear() + "-" +
                                    s.datetime().getMonthValue() + "-" +
                                    s.datetime().getDayOfMonth())
            );

    var totals = new ArrayList<DailyTotals>();

    dailySortedStats.forEach((key, dailyStatsRecords) -> {

      DailyTotals dt = new DailyTotals();
      dt.date(key);

      dailyStatsRecords.forEach(r -> {

        dt.dailyFeedCount(
                (dt.getDailyFeedCount() != null ? dt.getDailyFeedCount() : 0) +
                        (r.extraBottleFormulaeMilkQuantity() != null ? r.extraBottleFormulaeMilkQuantity() : 0) +
                        (r.extraBottleMotherMilkQuantity() != null ? r.extraBottleMotherMilkQuantity() : 0)
        );

        dt.dailyFeedTime(
                (dt.getDailyFeedTime() != null ? dt.getDailyFeedTime() : 0) +
                        (r.feedFromLeftDuration() != null ? r.feedFromLeftDuration() : 0) +
                        (r.feedFromRightDuration() != null ? r.feedFromRightDuration() : 0)
        );

        if (dt.getWeight() == null && r.weight() != null) {
          dt.setWeight(r.weight());
        }
      });
      totals.add(dt);
    });

    List<Stats> stats = dailySortedStats.keySet()
            .stream()
            .sorted((k1, k2) -> k2.compareTo(k1))
            .map(key -> {
              List<KidStatsRecord> kidStatsRecords = dailySortedStats.get(key);
              List<Stats> newList = new ArrayList<>(kidStatsRecords.size());
              // elements are already ordered
              int i = kidStatsRecords.size();
              for (KidStatsRecord r : kidStatsRecords) {
                Stats s = mapFromRecord(r);
                s.daycount((double) (i--));
                newList.add(s);
              }
              return newList;
            })
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());

    totals.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

    return new GetStatsResponse().stats(stats).dailyTotals(totals);

  }

  public UserRecord findByUsername(String username) {
    return userStore.find(username);
  }

  public void storeUser(UserRecord record) {
    userStore.store(record);
  }

  private KidStatsRecord mapFromStats(Stats s) {
    return new KidStatsRecord(
            null,
            userService.getCurrentUserId(),
            OffsetDateTime.parse(s.getDatetime()),
            s.getActivities().stream().map(GenericActivities::getValue).collect(Collectors.toList()),
            s.getTemperature(),
            s.getFeedFromLeftDuration(),
            s.getFeedFromRightDuration(),
            s.getPumpFromLeftQuantity(),
            s.getPumpFromRightQuantity(),
            s.getExtraBottleMotherMilkQuantity(),
            s.getWeight(),
            s.getExtraBottleFormulaeMilkQuantity(),
            s.getCreatedAt());
  }

  private Stats mapFromRecord(KidStatsRecord r) {

    return new Stats()
            .activities(
                    r.activities().stream().map(GenericActivities::fromValue).collect(Collectors.toList()))
            .datetime(r.datetime().format(DF))
            .feedFromLeftDuration(r.feedFromLeftDuration())
            .feedFromRightDuration(r.feedFromRightDuration())
            .pumpFromLeftQuantity(r.pumpFromLeftQuantity())
            .pumpFromRightQuantity(r.pumpFromRightQuantity())
            .extraBottleMotherMilkQuantity(r.extraBottleMotherMilkQuantity())
            .extraBottleFormulaeMilkQuantity(r.extraBottleFormulaeMilkQuantity())
            .temperature(r.temperature())
            .weight(r.weight());
  }
}
