package org.jzeratul.mykid;

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
import org.springframework.stereotype.Service;

@Service
public class StatsService {

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
				.sorted((r1, r2) -> {
					return r2.datetime().compareTo(r1.datetime());	
				})
				.collect(
						Collectors.groupingBy( 
								s -> s.datetime().getDayOfMonth() + "-" + 
										s.datetime().getMonthValue() + "-" + 
										s.datetime().getYear())
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
				
				if(dt.getWeight() == null && r.weight() != null) {
					dt.setWeight(r.weight());
				}
				
			});	
				
			totals.add(dt);
			
		});
		
		Map<String, List<Stats>> memoryWaste = dailySortedStats.entrySet()
				.stream()
				.collect(
					Collectors.toMap(
							entry -> entry.getKey(), 
							entry -> entry.getValue()
							.stream()
							.map(this::mapFromRecord)
							.collect(Collectors.toList())
					)
					);
		
		
		var stats = memoryWaste.values()
				.stream()
				.map( list -> {
					// elements are already ordered
					int i = list.size();
					for(Stats s: list) {
						s.daycount((double)(i--));
					}
					
					return list;
				})
				.flatMap(list -> list.stream())
				.collect(Collectors.toList());

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
				.datetime(r.datetime().toString())
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
