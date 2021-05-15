package org.jzeratul.mykid.storage;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomQueriesRepository {
	
	 @PersistenceContext
   private EntityManager entityManager;

   public List<DbKidStats> findStatsWithLimit(int limit, Long userid) {
       return entityManager.createQuery(
      		 "SELECT S FROM DbKidStats S where S.userid = :userid ORDER BY S.datetime DESC", DbKidStats.class
      		 )
      		 .setParameter("userid", userid)
      		 .setMaxResults(limit)
      		 .getResultList();
   }

   public Optional<DbKidSleep> lastRecordedSleep(Long userid) {
  	 
  	 List<DbKidSleep> results = entityManager.createQuery(
    		 "SELECT S FROM DbKidSleep S where S.userid = :userid ORDER BY S.startSleep DESC", DbKidSleep.class
    		 )
    		 .setParameter("userid", userid)
    		 .setMaxResults(1)
    		 .getResultList();
  	 
     return results != null && results.size() > 0 ? Optional.of(results.get(0)) : Optional.empty();
   }
}
