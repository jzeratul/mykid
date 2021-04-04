package org.jzeratul.mykid;

import org.jzeratul.mykid.model.User;

public interface MyKidSecurityService {

  User login(User user);

  User signup(User user);
}
