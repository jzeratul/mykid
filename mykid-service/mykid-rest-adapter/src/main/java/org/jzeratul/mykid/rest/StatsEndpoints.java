package org.jzeratul.mykid.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.jzeratul.mykid.api.MyKidApi;
import org.jzeratul.mykid.model.GetAdaptiveInputResponse;
import org.jzeratul.mykid.model.GetDailySleepResponse;
import org.jzeratul.mykid.model.GetDailyStatsResponse;
import org.jzeratul.mykid.model.GetSleepResponse;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.Sleep;
import org.jzeratul.mykid.model.Stats;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsEndpoints implements MyKidApi {

  private final StatsService statsService;
  
  private final AdaptiveInputService adaptiveInputService;

  public StatsEndpoints(StatsService statsService, AdaptiveInputService adaptiveInputService) {
    this.statsService = statsService;
		this.adaptiveInputService = adaptiveInputService;
  }

  @Override
  public ResponseEntity<Void> deleteSleep(@Valid Sleep sleep) {

    statsService.deleteSleep(sleep);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteStat(Stats stats) {

    statsService.deleteStat(stats);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<GetDailySleepResponse> getDailySleep() {

    return ResponseEntity.ok(new GetDailySleepResponse()
    		.dailySleepEntries(statsService.getDailySleep()));
  }


  @Override
  public ResponseEntity<GetDailyStatsResponse> getDailyStats(@Valid Optional<String> history) {  

    return ResponseEntity.ok(new GetDailyStatsResponse()
    		.dailyStatsEntries(statsService.getDailyStats(history)));
  }

  @Override
  public ResponseEntity<GetSleepResponse> getSleep() {
    return ResponseEntity.ok(statsService.getSleep());
  }

  @Override
  public ResponseEntity<GetStatsResponse> getStats() {
    return ResponseEntity.ok(statsService.getStats());
  }

  @Override
  public ResponseEntity<Void> postStat(@Valid Stats stats) {

    statsService.storeStats(stats);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> saveSleep(@Valid Sleep sleep) {

    statsService.storeSleep(sleep);
    return ResponseEntity.noContent().build();
  }

	@Override
	public ResponseEntity<GetAdaptiveInputResponse> getAdaptiveInput() {
		return ResponseEntity.ok(adaptiveInputService.getAdaptiveInput());
	}

}
