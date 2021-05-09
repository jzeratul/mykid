package org.jzeratul.mykid.rest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.jzeratul.mykid.model.GetAdaptiveInputResponse;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.storage.StatsDataStore;
import org.springframework.stereotype.Service;

@Service
public class AdaptiveInputService {

	private final StatsDataStore statsStore;
	private final CurrentUserService userService;

	public AdaptiveInputService(StatsDataStore statsStore, CurrentUserService userService) {
		this.statsStore = statsStore;
		this.userService = userService;
	}

	public GetAdaptiveInputResponse getAdaptiveInput() {

		List<KidStatsRecord> lastStats = statsStore.lastStats(20, userService.getCurrentUserId());

		DoubleSummaryStatistics averageFormulae = lastStats.stream()
				.map(KidStatsRecord::extraBottleFormulaeMilkQuantity)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));

		DoubleSummaryStatistics averageMother = lastStats.stream()
				.map(KidStatsRecord::extraBottleMotherMilkQuantity)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));
		
		DoubleSummaryStatistics averagePumpLeftDuration = lastStats.stream()
				.map(KidStatsRecord::pumpFromLeftQuantity)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));

		DoubleSummaryStatistics averagePumpRightDuration = lastStats.stream()
				.map(KidStatsRecord::pumpFromRightQuantity)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));
		
		DoubleSummaryStatistics averageFeedLeftDuration = lastStats.stream()
				.map(KidStatsRecord::feedFromLeftDuration)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));

		DoubleSummaryStatistics averageFeedRightDuration = lastStats.stream()
				.map(KidStatsRecord::feedFromRightDuration)
				.filter(s -> s > 0)
				.collect(Collectors.summarizingDouble(Double::doubleValue));
		
		return new GetAdaptiveInputResponse()
				.feedDurationLeft(buildAdaptiveInput(averageFeedLeftDuration))
				.feedDurationRight(buildAdaptiveInput(averageFeedRightDuration))
				.pumpLeft(buildAdaptiveInput(averagePumpLeftDuration))
				.pumpRight(buildAdaptiveInput(averagePumpRightDuration))
				.motherMilk(buildAdaptiveInput(averageMother))
				.formulaeMilk(buildAdaptiveInput(averageFormulae))
				;
	}

	private List<Double> buildAdaptiveInput(DoubleSummaryStatistics stats) {
		
		double average = stats.getAverage();

		if(average == 0d || average < 30d) {
			average = 30d;
		} else {
			// 114.98 becomes 120
			// we use double because it's simpler and less memory intensive w.r.t. BigDecimal
			// we cast to long because we just need a round value for the estimation of the next input
			// we don't use long overall because we want to support decimals
			average = Math.round(((long)average)/10) * 10;
		}
		
		return Arrays.asList(
				average - 40,
				average - 30,
				average - 20,
				average - 10,
				average,
				average + 10,
				average + 20,
				average + 30,
				average + 40,
				average + 50
				).stream().filter(a -> a > 0).toList();
	}
	
	public static void main(String[] args) {
		
		LocalDateTime ldt = LocalDateTime.now();
		OffsetDateTime odt = OffsetDateTime.now();
		
		
		System.out.println(ldt);
		System.out.println(odt);
		
		System.out.println("----");
		System.out.println(OffsetDateTime.of(ldt, ZoneOffset.UTC));
		System.out.println(odt.toLocalDateTime());
		
	}
}
