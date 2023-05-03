package dev.juunihana.adelaide.adelaide_api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String getUsername(String token);

  Boolean isExpired(String token);

  String createToken(UserDetails userDetails);

  Boolean isValid(String token, UserDetails userDetails);
}
