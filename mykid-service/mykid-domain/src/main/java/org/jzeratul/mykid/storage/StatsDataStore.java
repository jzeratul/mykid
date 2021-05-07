package org.jzeratul.mykid.storage;

import java.time.LocalDateTime;
import java.util.List;

import org.jzeratul.mykid.model.KidStatsRecord;
import org.jzeratul.mykid.model.SleepRecord;

public interface StatsDataStore {

  void delete(KidStatsRecord record);

  void delete(SleepRecord record);

  List<SleepRecord> getSleep(long userid);

  List<KidStatsRecord> getStats(LocalDateTime[] timeInterval, long userid);
  
  void storeSleep(SleepRecord record);
  
  void storeStats(KidStatsRecord record);
}
