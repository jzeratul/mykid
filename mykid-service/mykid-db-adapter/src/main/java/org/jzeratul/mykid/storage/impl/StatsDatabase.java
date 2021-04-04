package org.jzeratul.mykid.storage.impl;

import org.jzeratul.mykid.DbKidStats;
import org.jzeratul.mykid.DbKidStatsRepository;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.storage.StatsDataStore;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StatsDatabase implements StatsDataStore {

  private final DbKidStatsRepository statsRepository;

  public StatsDatabase(DbKidStatsRepository statsRepository) {
    this.statsRepository = statsRepository;
  }

  @Override
  public void storeStats(KidStatsRecord record) {
    statsRepository.save(mapToDatabaseEntity(record));
  }

  @Override
  public List<KidStatsRecord> getStats(OffsetDateTime start, OffsetDateTime end) {
    return statsRepository.findByCreatedAtBetween(start, end)
            .map(
                    list -> list.stream()
                            .map(this::mapToDomain)
                            .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
  }

  private DbKidStats mapToDatabaseEntity(KidStatsRecord record) {

    return new DbKidStats()
            .id(record.id())
            .activities(record.activities() != null ? String.join(",", record.activities()) : "")
            .createdAt(record.createdAt())
            .datetime(record.datetime())
            .extraBottleMotherMilkQuantity(record.extraBottleMotherMilkQuantity())
            .extraBottleFormulaeMilkQuantity(record.extraBottleFormulaeMilkQuantity())
            .temperature(record.temperature())
            .weight(record.weight())
            .feedFromRightDuration(record.feedFromRightDuration())
            .feedFromLeftDuration(record.feedFromLeftDuration())
            .pumpFromLeftQuantity(record.pumpFromLeftQuantity())
            .pumpFromRightQuantity(record.pumpFromRightQuantity());
  }

  private KidStatsRecord mapToDomain(DbKidStats stats) {

    return new KidStatsRecord(
            stats.id(),
            stats.datetime(),
            stats.activities() != null ? Stream.of(stats.activities().split(",")).toList() : null,
            stats.temperature(),
            stats.feedFromLeftDuration(),
            stats.feedFromRightDuration(),
            stats.pumpFromLeftQuantity(),
            stats.pumpFromRightQuantity(),
            stats.extraBottleMotherMilkQuantity(),
            stats.weight(),
            stats.extraBottleFormulaeMilkQuantity(),
            stats.createdAt()
    );
  }
}
