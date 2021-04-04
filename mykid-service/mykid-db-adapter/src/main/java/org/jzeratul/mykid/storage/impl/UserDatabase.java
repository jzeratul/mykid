package org.jzeratul.mykid.storage.impl;

import org.jzeratul.mykid.DbKidUser;
import org.jzeratul.mykid.DbKidUserRepository;
import org.jzeratul.mykid.model.UserRecord;
import org.jzeratul.mykid.storage.UserDataStore;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDatabase implements UserDataStore {

  private final DbKidUserRepository repository;

  public UserDatabase(DbKidUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public void store(UserRecord record) {
    repository.save(mapToDatabaseEntity(record));
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
            dbrecord.createdAt()
    );
  }
}
