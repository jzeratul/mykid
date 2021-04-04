package org.jzeratul.mykid.security;

import org.jzeratul.mykid.StatsService;
import org.jzeratul.mykid.model.User;
import org.jzeratul.mykid.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AppUserService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(AppUserService.class);

  private final StatsService statsService;

  public AppUserService(StatsService statsService) {
    this.statsService = statsService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    final UserRecord user = statsService.findByUsername(username);

    if (user != null) {
      return new AppUser(user.id(), user.username(), "", Collections.emptyList());

    } else {
      throw new UsernameNotFoundException("user not found");
    }
  }

  public User signup(User newUser) {

    var user = statsService.findByUsername(newUser.getUsername());

    if (user == null) {
      log.info("Attempted signup with existing username {}.", newUser.getUsername());
      return null;
    }

    statsService.storeUser(mapToRecord(newUser));

    return newUser;
  }

  private UserRecord mapToRecord(User newUser) {

    // todo encrypt pwd
    // todo shall we encrypt the username too?
    String pwd = newUser.getPassword();

    return new UserRecord(null, newUser.getUsername(), pwd, newUser.getCreatedAt());
  }
}
