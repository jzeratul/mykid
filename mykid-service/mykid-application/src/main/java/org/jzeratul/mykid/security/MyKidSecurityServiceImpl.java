package org.jzeratul.mykid.security;

import org.jzeratul.mykid.MyKidSecurityService;
import org.jzeratul.mykid.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class MyKidSecurityServiceImpl implements MyKidSecurityService {

  private static final Logger log = LoggerFactory.getLogger(MyKidSecurityServiceImpl.class);

  private final AuthenticationManager authenticationManager;
  private final AppUserService userDetailsService;
  private final JwtTokenService jwtToken;

  public MyKidSecurityServiceImpl(AuthenticationManager authenticationManager,
                                  AppUserService userDetailsService,
                                  JwtTokenService jwtToken) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtToken = jwtToken;
  }

  @Override
  public User login(User user) {

    authenticate(user);

    var userDetails = (AppUser) userDetailsService.loadUserByUsername(user.getUsername());

    var token = jwtToken.generateToken(userDetails);

    return new User().username(userDetails.getUsername()).jwtToken(token);
  }

  @Override
  public User signup(User user) {
    return userDetailsService.signup(user);
  }

  private void authenticate(User user) {
    try {
      authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    } catch (DisabledException e) {
      log.error("USER_DISABLED for {} {}", user.getUsername(), user.getPassword());
      throw new RuntimeException("USER_DISABLED");
    } catch (BadCredentialsException e) {
      log.error("INVALID_CREDENTIALS for {} {}", user.getUsername(), user.getPassword());
      throw new RuntimeException("INVALID_CREDENTIALS");
    }
  }
}
