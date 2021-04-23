package org.jzeratul.mykid.storage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidSleepRepository extends JpaRepository<DbKidSleep, Long> {

  Optional<List<DbKidSleep>> findByUseridAndStartSleepAfterAndEndSleepBeforeOrderByStartSleepDesc(Long userid, OffsetDateTime startSleep, OffsetDateTime endSleep);
}
