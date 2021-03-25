package org.jzeratul.mykid;

import org.jzeratul.mykid.api.MyKidApi;
import org.jzeratul.mykid.model.GetStatsResponse;
import org.jzeratul.mykid.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;

@RestController
public class StatsEndpoints implements MyKidApi {

  @Autowired
  private StatsService statsService;

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

    var resp = new GetStatsResponse().stats(stats);

    return ResponseEntity.ok(resp);
  }

  @Override
  public ResponseEntity<Void> postStat(@Valid Stats stats) {

    statsService.storeStats(stats);

    return ResponseEntity.noContent().build();
  }

}
