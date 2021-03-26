package org.jzeratul.mykid.storage;

import org.jzeratul.mykid.DbKidStats;
import org.jzeratul.mykid.DbKidStatsRepository;
import org.jzeratul.mykid.DbKidUserRepository;
import org.jzeratul.mykid.model.KidStatsRecord;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataStore {

  private final DbKidStatsRepository statsRepository;
  private final DbKidUserRepository userRepository;

  public DataStore(DbKidStatsRepository statsRepository, DbKidUserRepository userRepository) {
    this.statsRepository = statsRepository;
    this.userRepository = userRepository;
  }

  public void storeStats(KidStatsRecord record) {
    statsRepository.save(mapToDbKidStats(record));
  }

  public List<KidStatsRecord> getStats(OffsetDateTime start, OffsetDateTime end) {
    return statsRepository.findByCreatedAtBetween(start, end)
            .map(
                    list -> list.stream()
                            .map(db -> mapFromDbKidStats(db))
                            .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
  }

  private DbKidStats mapToDbKidStats(KidStatsRecord record) {

    return new DbKidStats()
            .setId(record.id())
            .setActivities(record.activities() != null ? String.join(",", record.activities()) : "")
            .setCreatedAt(record.createdAt())
            .setDatetime(record.datetime())
            .setExtraBottleMotherMilkQuantity(record.extraBottleMotherMilkQuantity())
            .setExtraBottleFormulaeMilkQuantity(record.extraBottleFormulaeMilkQuantity())
            .setTemperature(record.temperature())
            .setWeight(record.weight())
            .setFeedFromRightDuration(record.feedFromRightDuration())
            .setFeedFromLeftDuration(record.feedFromLeftDuration())
            .setPumpFromLeftQuantity(record.pumpFromLeftQuantity())
            .setPumpFromRightQuantity(record.pumpFromRightQuantity());
  }

  private KidStatsRecord mapFromDbKidStats(DbKidStats db) {

    return new KidStatsRecord(
            db.getId(),
            db.getDatetime(),
            db.getActivities() != null ? Stream.of(db.getActivities().split(",")).toList() : null,
            db.getTemperature(),
            db.getFeedFromLeftDuration(),
            db.getFeedFromRightDuration(),
            db.getPumpFromLeftQuantity(),
            db.getPumpFromRightQuantity(),
            db.getExtraBottleMotherMilkQuantity(),
            db.getWeight(),
            db.getExtraBottleFormulaeMilkQuantity(),
            db.getCreatedAt()
    );
  }
}
