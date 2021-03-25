package org.jzeratul.mykid;

import org.jzeratul.mykid.model.GenericActivities;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.Stats;
import org.jzeratul.mykid.storage.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

  @Autowired
  private DataStore dataStore;

  public void storeStats(Stats stats) {
    dataStore.storeStats(mapFromStats(stats));
  }

  private KidStatsRecord mapFromStats(Stats s) {
    return new KidStatsRecord(
            null,
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
            s.getCreatedAt()
    );
  }

  public List<Stats> getStats(OffsetDateTime start, OffsetDateTime end) {
    return dataStore.getStats(start, end)
            .stream()
            .map(this::mapFromRecord)
            .collect(Collectors.toList())
            ;
  }

  private Stats mapFromRecord(KidStatsRecord r) {

    return new Stats()
            .activities(
                    r.activities()
                            .stream()
                            .map(GenericActivities::fromValue)
                            .collect(Collectors.toList()))
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
