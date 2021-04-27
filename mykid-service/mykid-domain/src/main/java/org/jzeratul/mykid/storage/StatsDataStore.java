package org.jzeratul.mykid.storage;

import java.time.OffsetDateTime;
import java.util.List;

import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.SleepRecord;

public interface StatsDataStore {

  void storeStats(KidStatsRecord record);

  List<KidStatsRecord> getStats(long userid);

  void delete(KidStatsRecord record);

  void delete(SleepRecord record);
  
  void storeSleep(SleepRecord record);
  
  List<SleepRecord> getSleep(long userid);
}
