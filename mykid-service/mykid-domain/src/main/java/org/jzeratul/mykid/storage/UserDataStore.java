package org.jzeratul.mykid.storage;

import org.jzeratul.mykid.model.UserRecord;

public interface UserDataStore {

  void store(UserRecord record);

  UserRecord find(String username);
}
