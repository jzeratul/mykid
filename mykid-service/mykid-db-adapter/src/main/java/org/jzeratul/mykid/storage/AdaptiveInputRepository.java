package org.jzeratul.mykid.storage;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AdaptiveInputRepository {
	
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

}
