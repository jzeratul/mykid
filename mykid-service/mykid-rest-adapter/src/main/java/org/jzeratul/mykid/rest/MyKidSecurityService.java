package org.jzeratul.mykid.rest;

import org.jzeratul.mykid.model.User;

public interface MyKidSecurityService {

  User login(User user);

  User signup(User user);
}
