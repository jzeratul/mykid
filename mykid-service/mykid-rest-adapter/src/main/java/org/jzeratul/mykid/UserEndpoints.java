package org.jzeratul.mykid;

import org.jzeratul.mykid.api.UserApi;
import org.jzeratul.mykid.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEndpoints implements UserApi {

  private static final Logger log = LoggerFactory.getLogger(UserEndpoints.class);
  
  private final MyKidSecurityService securityService;

  public UserEndpoints(MyKidSecurityService securityService) {
    this.securityService = securityService;
  }

  @Override
  public ResponseEntity<User> login(User user) {
    return ResponseEntity.ok(securityService.login(user));
  }

  @Override
  public ResponseEntity<User> signup(User user) {
		return ResponseEntity.ok(securityService.signup(user));
  }
	
}
