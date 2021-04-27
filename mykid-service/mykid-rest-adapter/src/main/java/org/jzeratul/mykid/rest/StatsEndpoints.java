package org.jzeratul.mykid.rest;

import org.jzeratul.mykid.api.MyKidApi;
import org.jzeratul.mykid.model.GetDailyStatsResponse;
import org.jzeratul.mykid.model.GetSleepResponse;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.Sleep;
import org.jzeratul.mykid.model.Stats;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StatsEndpoints implements MyKidApi {

  private final StatsService statsService;

  public StatsEndpoints(StatsService statsService) {
    this.statsService = statsService;
  }

  @Override
  public ResponseEntity<Void> deleteStat(Stats stats) {

    statsService.deleteStat(stats);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<GetStatsResponse> getStats() {

    var stats = statsService.getStats();

    return ResponseEntity.ok(stats);
  }


  @Override
  public ResponseEntity<Void> postStat(@Valid Stats stats) {

    statsService.storeStats(stats);

    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<GetDailyStatsResponse> getDailyStats() {

    var stats = statsService.getStats();

    GetDailyStatsResponse resp = new GetDailyStatsResponse();
    resp.setDailyStats(stats.getDailyStats());

    return ResponseEntity.ok(resp);
  }

  @Override
  public ResponseEntity<GetSleepResponse> getSleep() {

    var sleep = statsService.getSleep();

    return ResponseEntity.ok(sleep);
  }

  @Override
  public ResponseEntity<Void> saveSleep(@Valid Sleep sleep) {

    statsService.storeSleep(sleep);

    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteSleep(@Valid Sleep sleep) {

    statsService.deleteSleep(sleep);
    return ResponseEntity.ok().build();
  }

}
