package org.jzeratul.mykid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbKidUserRepository extends JpaRepository<DbKidUser, Long> {
}
