package org.jzeratul.mykid.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DbKidUserRepository extends JpaRepository<DbKidUser, Long> {

  Optional<DbKidUser> findByUsername(String username);
}
