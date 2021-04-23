package org.jzeratul.mykid.storage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidStatsRepository extends JpaRepository<DbKidStats, Long> {

  Optional<List<DbKidStats>> findByUseridAndCreatedAtBetweenOrderByDatetimeDesc(Long userid, OffsetDateTime start, OffsetDateTime end);
}
