package dev.juunihana.adelaide.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String getUsername(String token);

  Boolean isExpired(String token);

  String createToken(String username);

  Boolean isValid(String token, UserDetails userDetails);
}
