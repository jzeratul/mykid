package org.jzeratul.mykid.storage.impl;

import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.SleepRecord;
import org.jzeratul.mykid.storage.DbKidSleep;
import org.jzeratul.mykid.storage.DbKidStats;
import org.jzeratul.mykid.storage.KidSleepRepository;
import org.jzeratul.mykid.storage.KidStatsRepository;
import org.jzeratul.mykid.storage.StatsDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StatsDatabase implements StatsDataStore {
	
  private static final Logger log = LoggerFactory.getLogger(StatsDatabase.class);

  private final KidStatsRepository statsRepository;
  private final KidSleepRepository kidSleepRepository;

  public StatsDatabase(KidStatsRepository statsRepository, KidSleepRepository kidSleepRepository) {
    this.statsRepository = statsRepository;
		this.kidSleepRepository = kidSleepRepository;
  }

  @Override
  public void storeStats(KidStatsRecord record) {
    statsRepository.save(mapToDatabaseEntity(record));
  }

  @Override
  public List<KidStatsRecord> getStats(long userid) {

    final Optional<List<DbKidStats>> dbKidStats = statsRepository.findLastEntries(userid);

    List<KidStatsRecord> data = dbKidStats
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

  @Override
  public void delete(SleepRecord record) {
  	kidSleepRepository.delete(mapToDatabaseEntity(record));
  }

	@Override
	public void storeSleep(SleepRecord record) {
		kidSleepRepository.save(mapToDatabaseEntity(record));
	}

	@Override
	public List<SleepRecord> getSleep(long userid) {

		List<SleepRecord> data = kidSleepRepository.findSleep(userid)
		.map(
		    list -> list.stream()
		    .map(this::mapToDomain)
		    .collect(Collectors.toList())
		    )
		.orElse(Collections.emptyList());
    
    if(log.isDebugEnabled()) {
    	String log1 = data.stream()
    			.map(s -> s.id() + 
    					", " + 
    					s.startSleep().toString() + 
    					", " + 
    					s.endSleep().toString() + 
    					", " +
    					s.getSleepDuration().toMinutes() + "min"
		
			).collect(Collectors.joining("\n"));

      log.debug("""
                       =========================================================================
                      SleepTable\s
                       sleeps:
                       {}\s
                      """,
              log1);
    }
		return data;
	}
	
	private SleepRecord mapToDomain(DbKidSleep sleep) {
		return new SleepRecord(
				sleep.getId(), 
				sleep.getStartSleep(), 
				sleep.getEndSleep(), 
				sleep.getCreatedAt());
	}
	
	private DbKidSleep mapToDatabaseEntity(SleepRecord record) {
		return new DbKidSleep()
				.setId(record.id())
				.setCreatedAt(record.createdAt())
				.setStartSleep(record.startSleep())
				.setEndSleep(record.endSleep());
	}
	
}
