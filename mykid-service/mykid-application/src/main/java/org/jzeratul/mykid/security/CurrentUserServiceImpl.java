package org.jzeratul.mykid.security;

import org.jzeratul.mykid.CurrentUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	@Override
	public long getCurrentUserId() {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();	
  	AppUser currentUser = (AppUser) authToken.getPrincipal();
  
  	return currentUser.userId();
	}
}
