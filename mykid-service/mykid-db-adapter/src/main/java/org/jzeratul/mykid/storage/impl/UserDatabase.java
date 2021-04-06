package org.jzeratul.mykid.storage.impl;

import org.jzeratul.mykid.DbKidUser;
import org.jzeratul.mykid.DbKidUserRepository;
import org.jzeratul.mykid.model.UserRecord;
import org.jzeratul.mykid.storage.UserDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDatabase implements UserDataStore {

  private static final Logger log = LoggerFactory.getLogger(UserDatabase.class);
  
  private final DbKidUserRepository repository;

  public UserDatabase(DbKidUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public void store(UserRecord record) {
  	log.debug("storing {}", record);
    DbKidUser save = repository.save(mapToDatabaseEntity(record));
    log.debug("stored user {} id {}", save.username(), save.id());
  }

  @Override
  public UserRecord find(String username) {
    return mapToDomain(repository.findByUsername(username));
  }

  private DbKidUser mapToDatabaseEntity(UserRecord record) {

    return new DbKidUser()
            .id(record.id())
            .createdAt(record.createdAt())
            .username(record.username())
            .email(record.email())
            .password(record.password());
  }

  private UserRecord mapToDomain(Optional<DbKidUser> db) {

    if (db.isEmpty()) {
      return null;
    }
    DbKidUser dbrecord = db.get();
    return new UserRecord(
            dbrecord.id(),
            dbrecord.username(),
            dbrecord.password(),
            dbrecord.email(),
            dbrecord.createdAt()
    );
  }
}
