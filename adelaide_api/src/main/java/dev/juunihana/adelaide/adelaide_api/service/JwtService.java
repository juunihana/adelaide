package dev.juunihana.adelaide.adelaide_api.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String getUsername(String token);

  Boolean isExpired(String token);

  String createToken(Authentication authentication);

  Boolean isValid(String token, UserDetails userDetails);
}
