package org.jzeratul.mykid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DbKidStatsRepository extends JpaRepository<DbKidStats, Long> {

  Optional<List<DbKidStats>> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}
