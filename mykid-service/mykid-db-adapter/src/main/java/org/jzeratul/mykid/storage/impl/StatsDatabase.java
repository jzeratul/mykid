package org.jzeratul.mykid.storage.impl;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.storage.DbKidStats;
import org.jzeratul.mykid.storage.DbKidStatsRepository;
import org.jzeratul.mykid.storage.StatsDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StatsDatabase implements StatsDataStore {
	
  private static final Logger log = LoggerFactory.getLogger(StatsDatabase.class);

  private final DbKidStatsRepository statsRepository;

  public StatsDatabase(DbKidStatsRepository statsRepository) {
    this.statsRepository = statsRepository;
  }

  @Override
  public void storeStats(KidStatsRecord record) {
    statsRepository.save(mapToDatabaseEntity(record));
  }

  @Override
  public List<KidStatsRecord> getStats(OffsetDateTime start, OffsetDateTime end, long userid) {
    List<KidStatsRecord> data = statsRepository.findByUseridAndCreatedAtBetweenOrderByDatetimeDesc(userid, start, end)
            .map(
                    list -> list.stream()
                            .map(this::mapToDomain)
                            .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    

    
    if(log.isDebugEnabled()) {
    	String log1 = data.stream().map(s -> 
    	s.id() + "," + s.weight() + "," + s.datetime().toString() + ","
		
			).collect(Collectors.joining("\n"));
    
    	
    	log.debug("\n =========================================================================\n"
    			+ "StatsDatabase \n stats:\n {} \n", log1);
    }
    
		return data;
  }

  @Override
  public void delete(KidStatsRecord record) {
    statsRepository.delete(mapToDatabaseEntity(record));
  }

  private DbKidStats mapToDatabaseEntity(KidStatsRecord record) {

    return new DbKidStats()
            .id(record.id())
            .userid(record.userid())
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
            stats.userid(),
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
