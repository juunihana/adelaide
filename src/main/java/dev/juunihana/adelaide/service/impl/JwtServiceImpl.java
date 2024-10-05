package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private String SECRET_KEY = "4d3l41d3_1337";

  @Override
  public String getUsername(String token) {
    Function<Claims, String> claimsResolver = Claims::getSubject;
    return claimsResolver.apply(
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
  }

  @Override
  public Boolean isExpired(String token) {
    Function<Claims, Date> claimsResolver = Claims::getExpiration;
    return claimsResolver.apply(
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()).before(new Date());
  }

  @Override
  public String createToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", username);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 days
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  @Override
  public Boolean isValid(String token, UserDetails userDetails) {
    return (userDetails.getUsername().equals(getUsername(token))) && !isExpired(token);
  }
}
