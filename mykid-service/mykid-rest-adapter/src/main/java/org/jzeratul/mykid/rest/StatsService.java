package org.jzeratul.mykid.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jzeratul.mykid.model.DailySleep;
import org.jzeratul.mykid.model.DailyStat;
import org.jzeratul.mykid.model.GenericActivities;
import org.jzeratul.mykid.model.GetSleepResponse;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.Sleep;
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
  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MMM-YYYY");

	private final EncryptorService encryptorService;
	private final StatsDataStore statsStore;
	private final CurrentUserService userService;
	private final UserDataStore userStore;

	public StatsService(StatsDataStore statsStore, UserDataStore userStore, CurrentUserService userService,
			EncryptorService encryptorService) {
		this.userService = userService;
		this.statsStore = statsStore;
		this.userStore = userStore;
		this.encryptorService = encryptorService;
	}

	public void deleteSleep(Sleep sleep) {
		statsStore.delete(mapToRecord(sleep));
	}

	public void deleteStat(Stats stats) {
		statsStore.delete(mapToRecord(stats));
	}

	public UserRecord findByUsername(String username) {
		return userStore.find(username);
	}

	public List<DailySleep> getDailySleep() {

		var results = new ArrayList<DailySleep>();

		List<SleepRecord> sleep = statsStore.getSleep(userService.getCurrentUserId());
		ListIterator<SleepRecord> listIterator = sleep.listIterator(sleep.size());

		LocalDate currentDay = null;
		DailySleep daySleep = null;
		while(listIterator.hasNext()) {
			SleepRecord r = listIterator.next();
						
			if(currentDay == null || !currentDay.equals(r.getSleepDay())) {
				currentDay = r.getSleepDay();
				daySleep = new DailySleep();
				results.add(daySleep);
				daySleep.date(currentDay.format(DF));
				daySleep.setTotalSleep(Double.valueOf(r.getSleepDuration().toMinutes()));
			} else {
				daySleep.setTotalSleep(daySleep.getTotalSleep() + r.getSleepDuration().toMinutes());
			}
		}
		
		return results;
	}

	public List<DailyStat> getDailyStats() {
		
		var results = new ArrayList<DailyStat>();
		// because of the weight which is not measured at each entry, we traverse backwards
		List<KidStatsRecord> stats = statsStore.getStats(userService.getCurrentUserId());
		ListIterator<KidStatsRecord> listIterator = stats.listIterator(stats.size());
		
		LocalDate currentDay = null;
		DailyStat dayStat = null;
		double lastWeight = 0;
		
		while(listIterator.hasPrevious()) {
			KidStatsRecord r = listIterator.previous();
			
			if(currentDay == null || !currentDay.equals(r.getDay())) {
				currentDay = r.getDay();
				dayStat = new DailyStat();
				results.add(dayStat);
				dayStat.dailyFeedQuantity(0d);
				dayStat.dailyFeedTime(0d);
				dayStat.setWeight(0d);
				dayStat.date(currentDay.format(DF));
			}
			
			dayStat.activities(r.activities().stream().map(GenericActivities::fromValue).toList());
			dayStat.dailyFeedQuantity(dayStat.getDailyFeedQuantity() + r.totalFeedQuantity());
			dayStat.dailyFeedTime(dayStat.getDailyFeedTime() + r.totalFeedDuration());
			
			if(r.weight() > 0) {
				lastWeight = r.weight();
			}
			
			dayStat.weight(lastWeight);
		}
		
		Collections.reverse(results);
		
		return results;
	}

	public GetSleepResponse getSleep() {
		
		List<Sleep> entries = statsStore.getSleep(userService.getCurrentUserId())
				.stream()
				.map(this::mapFromRecord)
				.collect(Collectors.toList());

		return new GetSleepResponse().sleepEntries(entries);
	}

	public GetStatsResponse getStats() {

		var stats = statsStore.getStats(userService.getCurrentUserId())
				.stream()
				.map(this::mapFromRecord)
				.collect(Collectors.toList());
		
		if (log.isDebugEnabled()) {
			var log1 = stats.stream().map(s -> {
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
					 """, log1);
		}

		return new GetStatsResponse().statsEntries(stats);

	}

	public void storeSleep(@Valid Sleep sleep) {
		statsStore.storeSleep(mapToRecord(sleep));
	}

	public void storeStats(Stats stats) {
		statsStore.storeStats(mapToRecord(stats));
	}

	public void storeUser(UserRecord record) {
		userStore.store(record);
	}

	private Stats mapFromRecord(KidStatsRecord r) {

		return new Stats()
				.token(encryptorService.encLong(r.id()))
				.activities(
						r.activities().stream()
						.map(GenericActivities::fromValue)
						.collect(Collectors.toList()))
				.datetime(r.datetime())
				.feedFromLeftDuration(r.feedFromLeftDuration())
				.feedFromRightDuration(r.feedFromRightDuration())
				.pumpFromLeftQuantity(r.pumpFromLeftQuantity())
				.pumpFromRightQuantity(r.pumpFromRightQuantity())
				.extraBottleMotherMilkQuantity(r.extraBottleMotherMilkQuantity())
				.extraBottleFormulaeMilkQuantity(r.extraBottleFormulaeMilkQuantity())
				.temperature(r.temperature())
				.weight(r.weight())
				.daycount(r.dayFeedCount());
	}

	private Sleep mapFromRecord(SleepRecord record) {
		return new Sleep().token(encryptorService.encLong(record.id()))
				.createdAt(record.createdAt())
				.sleepStart(record.startSleep())
				.sleepEnd(record.endSleep());
	}

	private SleepRecord mapToRecord(@Valid Sleep sleep) {

		return new SleepRecord(
				sleep.getToken() == null ? null : encryptorService.decLong(sleep.getToken()),
				sleep.getCreatedAt(),
				sleep.getSleepStart(),
				sleep.getSleepEnd());
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
				s.getCreatedAt(),
				0);
	}
}
