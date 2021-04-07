package org.jzeratul.mykid.storage;

import org.jzeratul.mykid.model.KidStatsRecord;

import java.time.OffsetDateTime;
import java.util.List;

public interface StatsDataStore {

  void storeStats(KidStatsRecord record);

  List<KidStatsRecord> getStats(OffsetDateTime start, OffsetDateTime end, long userid);
}
