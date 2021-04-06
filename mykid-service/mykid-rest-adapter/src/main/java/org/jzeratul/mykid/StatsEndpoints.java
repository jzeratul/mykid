package org.jzeratul.mykid;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.jzeratul.mykid.api.MyKidApi;
import org.jzeratul.mykid.model.GetStatsResponse;
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

}
