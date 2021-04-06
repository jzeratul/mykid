package org.jzeratul.mykid.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * We override this class because we need an additional attribute to use in the queries: the userid
 */
public class AppUser extends User {
	
	private static final long serialVersionUID = -2150411866275142569L;
	private Long userId;
	private String email;
	
	public AppUser(Long id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		
		this.userId = id;
	}

  public Long userId() {
    return userId;
  }
  
  public String email() {
  	return email;
  }
}
