package org.jzeratul.mykid.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KidSleepRepository extends JpaRepository<DbKidSleep, Long> {

  @Query("from DbKidSleep S where S.userid = :userid order by S.startSleep DESC  ")
  Optional<List<DbKidSleep>> findSleep(Long userid, Pageable pageable);

}
