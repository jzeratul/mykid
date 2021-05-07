package org.jzeratul.mykid.storage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KidStatsRepository extends JpaRepository<DbKidStats, Long> {

  @Query("from DbKidStats S where S.userid = :userid order by S.datetime DESC ")
  Optional<List<DbKidStats>> findLastEntries(@Param("userid") Long userid, Pageable pageable);
  
  
  Optional<List<DbKidStats>> findByUseridOrderByDatetimeDesc(@Param("userid") Long userid);

  
  @Query("from DbKidStats S where S.userid = :userid and S.datetime >=:start and S.datetime <=:end order by S.datetime DESC ")
  Optional<List<DbKidStats>> findByInterval(
  		@Param("userid") Long userid,
  		@Param("start") OffsetDateTime start,
  		@Param("end") OffsetDateTime end);
}
