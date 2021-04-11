package org.jzeratul.mykid.rest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StatsService {

  private static final Logger log = LoggerFactory.getLogger(StatsService.class);
//  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MMM-YYYY HH:mm");
  private static final ObjectMapper OM = new ObjectMapper();

  private final CurrentUserService userService;
  private final StatsDataStore statsStore;
  private final UserDataStore userStore;
  private final EncryptorService encryptorService;

  public StatsService(StatsDataStore statsStore, UserDataStore userStore, CurrentUserService userService, EncryptorService encryptorService) {
    this.userService = userService;
    this.statsStore = statsStore;
    this.userStore = userStore;
    this.encryptorService = encryptorService;
  }

  public void storeStats(Stats stats) {
    statsStore.storeStats(mapFromStats(stats));
  }

  public GetStatsResponse getStats(OffsetDateTime start, OffsetDateTime end) {

    Map<LocalDate, List<KidStatsRecord>> dailySortedStats = statsStore.getStats(start, end, userService.getCurrentUserId())
            .stream()
            .collect(Collectors.groupingBy(s -> s.datetime().toLocalDate()));

    var totals = new ArrayList<DailyTotals>();

    dailySortedStats.forEach((key, dailyStatsRecords) -> {

      DailyTotals dt = new DailyTotals();
      dt.date(key.toString());

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

    
    if(log.isDebugEnabled()) {
    	String log1 = stats.stream().map(s -> {
				try {
					return OM.writeValueAsString(s);
				} catch (JsonProcessingException e) {
					return e.getMessage();
				}
			}).collect(Collectors.joining("\n"));
    	
    	String log2 = totals.stream().map(s -> {
				try {
					return OM.writeValueAsString(s);
				} catch (JsonProcessingException e) {
					return e.getMessage();
				}
			}).collect(Collectors.joining("\n"));
    	
    	log.debug("\n =========================================================================\n"
    			+ "StatsService \n stats:\n {} \n"
    			+ " =========================================================================\n "
    			+ "totals\n{}", log1, log2);
    }
    
    return new GetStatsResponse().stats(stats).dailyTotals(totals);

  }

  public UserRecord findByUsername(String username) {
    return userStore.find(username);
  }

  public void storeUser(UserRecord record) {
    userStore.store(record);
  }

  public void deleteStat(Stats stats) {
    statsStore.delete(mapFromStats(stats));
  }

  private KidStatsRecord mapFromStats(Stats s) {
  	
		log.debug("Saving dates: from request {} to offset {}", 
				s.getDatetime());
  	
  	
    return new KidStatsRecord(
            s.getToken() == null ? null : encryptorService.decLong(s.getToken()),
            userService.getCurrentUserId(),
            s.getDatetime(),
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
            .token(encryptorService.encLong(r.id()))
            .activities(
                    r.activities().stream().map(GenericActivities::fromValue).collect(Collectors.toList()))
            .datetime(r.datetime())
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
