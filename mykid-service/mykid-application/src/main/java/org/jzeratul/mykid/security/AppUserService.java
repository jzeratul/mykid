package org.jzeratul.mykid.security;

import java.util.Collections;

import org.jzeratul.mykid.StatsService;
import org.jzeratul.mykid.model.User;
import org.jzeratul.mykid.model.UserRecord;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

  private final StatsService statsService;
  private final PasswordEncoder bCryptPasswordEncoder;

  public AppUserService(StatsService statsService, PasswordEncoder bCryptPasswordEncoder) {
    this.statsService = statsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    final UserRecord user = statsService.findByUsername(username);

    if (user != null) {
      return new AppUser(user.id(), user.username(), user.password(), Collections.emptyList());

    } else {
      throw new UsernameNotFoundException("user not found");
    }
  }

  public User signup(User newUser) {

    var user = statsService.findByUsername(newUser.getUsername());

    if (user != null) {
      throw new RuntimeException("Attempted signup with existing username " + newUser.getUsername());
    }

    statsService.storeUser(mapToRecord(newUser));

    return newUser;
  }

  private UserRecord mapToRecord(User newUser) {

    String pwd = bCryptPasswordEncoder.encode(newUser.getPassword());

    return new UserRecord(null, newUser.getUsername(), pwd, newUser.getCreatedAt());
  }
}
