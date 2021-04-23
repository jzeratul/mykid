package org.jzeratul.mykid.rest;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.jzeratul.mykid.api.MyKidApi;
import org.jzeratul.mykid.model.GetSleepResponse;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.GetStatsSummaryResponse;
import org.jzeratul.mykid.model.Sleep;
import org.jzeratul.mykid.model.Stats;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<GetStatsResponse> getStats(Optional<String> startDate, Optional<String> endDate) {

    OffsetDateTime start;
    OffsetDateTime end;

    if(startDate.isEmpty() || endDate.isEmpty()) {
      end = OffsetDateTime.now();
      start = end.minusDays(7);
    } else {
      start = OffsetDateTime.parse(startDate.get());
      end = OffsetDateTime.parse(endDate.get());
    }

    var stats = statsService.getStats(start, end);

    return ResponseEntity.ok(stats);
  }
  

  @Override
  public ResponseEntity<Void> postStat(@Valid Stats stats) {

    statsService.storeStats(stats);

    return ResponseEntity.noContent().build();
  }

	@Override
	public ResponseEntity<GetStatsSummaryResponse> getStatsSummary() {

    OffsetDateTime start = OffsetDateTime.now();
    OffsetDateTime end = start.minusDays(1).withHour(0).withMinute(0);
    
    var stats = statsService.getStats(start, end);
    
		GetStatsSummaryResponse resp = new GetStatsSummaryResponse();
		resp.setTotals(stats.getDailyTotals());
		
		return ResponseEntity.ok(resp);
	}

	@Override
	public ResponseEntity<GetSleepResponse> getSleep() {

    OffsetDateTime end = OffsetDateTime.now();
    OffsetDateTime start = end.minusDays(7);

    var sleep = statsService.getSleep(start, end);
		
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
