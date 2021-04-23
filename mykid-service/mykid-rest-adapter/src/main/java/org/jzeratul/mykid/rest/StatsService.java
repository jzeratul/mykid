package org.jzeratul.mykid.rest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jzeratul.mykid.model.DailyTotals;
import org.jzeratul.mykid.model.GenericActivities;
import org.jzeratul.mykid.model.GetSleepResponse;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.Sleep;
import org.jzeratul.mykid.model.SleepDailyTotal;
import org.jzeratul.mykid.model.SleepRecord;
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
    statsStore.storeStats(mapToRecord(stats));
  }

  public GetStatsResponse getStats(OffsetDateTime start, OffsetDateTime end) {

    Map<LocalDate, List<KidStatsRecord>> dailySortedStats = statsStore.getStats(start, end, userService.getCurrentUserId())
            .stream()
            .collect(Collectors.groupingBy(s -> s.getDay()));

    var totals = new ArrayList<DailyTotals>();
    double lastKnownWeight = 0;

    for (Map.Entry<LocalDate, List<KidStatsRecord>> dailyTotals : dailySortedStats.entrySet()) {
		
    	 DailyTotals dt = new DailyTotals();
       dt.date(dailyTotals.getKey().toString());
       dt.setDailyFeedQuantity(0d);
       dt.setDailyFeedTime(0d);
       dt.setWeight(lastKnownWeight);

       for (KidStatsRecord r : dailyTotals.getValue()) {
      	 dt.dailyFeedQuantity(dt.getDailyFeedQuantity() + r.totalFeedQuantity());
         dt.dailyFeedTime(dt.getDailyFeedTime() + r.totalFeedDuration());

         if (r.weight() > 0) {
           dt.setWeight(r.weight());
           lastKnownWeight = r.weight();
         } 
       }
       
       totals.add(dt);
		}
    
    List<Stats> stats = dailySortedStats.keySet()
            .stream()
            .sorted(Comparator.reverseOrder())
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
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    totals.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

    
    if(log.isDebugEnabled()) {
    	String log1 = stats.stream().map(s -> {
				try {
					return new ObjectMapper().writeValueAsString(s);
				} catch (JsonProcessingException e) {
					return e.getMessage();
				}
			}).collect(Collectors.joining("\n"));
    	
    	String log2 = totals.stream().map(s -> {
				try {
					return new ObjectMapper().writeValueAsString(s);
				} catch (JsonProcessingException e) {
					return e.getMessage();
				}
			}).collect(Collectors.joining("\n"));

      log.debug("""
               =========================================================================
              StatsService\s
               stats:
               {}\s
               =========================================================================
               totals
              {}""",
              log1,
              log2);
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
    statsStore.delete(mapToRecord(stats));
  }

  private KidStatsRecord mapToRecord(Stats s) {
  	
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

	public void storeSleep(@Valid Sleep sleep) {
		statsStore.storeSleep(mapToRecord(sleep));
	}

	public GetSleepResponse getSleep(OffsetDateTime start, OffsetDateTime end) {
		List<SleepRecord> sleeps = statsStore.getSleep(start, end, userService.getCurrentUserId());
		
		Map<LocalDate, List<SleepRecord>> sorted = sleeps.stream().collect(Collectors.groupingBy(s -> s.getSleepDay()));
		
		List<SleepDailyTotal> totals = new ArrayList<>();
		
		sorted.forEach((key, dailySleepRecords) -> {
			
			SleepDailyTotal total = new SleepDailyTotal();
			total.setTotalSleep(0d);
			total.date(key.toString());
			
			dailySleepRecords.forEach(r -> {
				total.setTotalSleep(total.getTotalSleep() + r.getSleepDuration().toMinutes());
			});
			totals.add(total);
		});
			
		List<Sleep> entries = sleeps.stream()
				.map(s -> mapFromRecord(s))
				.collect(Collectors.toList());
		
		return new GetSleepResponse()
				.sleepDailyTotals(totals)
				.sleepEntries(entries);
	}

  public void deleteSleep(Sleep sleep) {
    statsStore.delete(mapToRecord(sleep));
  }

	private SleepRecord mapToRecord(@Valid Sleep sleep) {
		
		return new SleepRecord(
					sleep.getToken() == null ? null : encryptorService.decLong(sleep.getToken()),
					sleep.getCreatedAt(),
					sleep.getSleepStart(),
					sleep.getSleepEnd()
				);
	}
	
	private Sleep mapFromRecord(SleepRecord record) {
		return new Sleep()
				.token(encryptorService.encLong(record.id()))
				.createdAt(record.createdAt())
				.sleepStart(record.startSleep())
				.sleepEnd(record.endSleep());
	}
}
