package org.jzeratul.mykid.security;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

  private final AppUserService jwtUserDetailsService;
  private final JwtTokenService jwtTokenService;

  public JwtRequestFilter(AppUserService jwtUserDetailsService, JwtTokenService jwtTokenService) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtTokenService = jwtTokenService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    log.debug("JwtRequestFilter {} {} {} {}", request.getRequestURI(), request.getContentType(), request.getMethod(), map(request.getParameterMap()));

    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenService.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        log.error("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        log.error("JWT Token has expired");
        response.setStatus(HttpStatus.FORBIDDEN.value());
      }
    } else {
      log.warn("JWT Token does not begin with Bearer String");
    }

    //Once we get the token validate it.
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      AppUser userDetails = (AppUser) this.jwtUserDetailsService.loadUserByUsername(username);

      // if token is valid configure Spring Security to manually set authentication
      if (jwtTokenService.validateToken(jwtToken, userDetails)) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    chain.doFilter(request, response);
  }

	private Object map(Map<String, String[]> parameterMap) {
		String mapAsString = parameterMap.keySet().stream()
	      .map(key -> key + "=" + parameterMap.get(key))
	      .collect(Collectors.joining(", ", "{", "}"));
	    return mapAsString;
	}
}
