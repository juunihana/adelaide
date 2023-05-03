package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.repository.UserAuthRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

  private final UserAuthRepository userAuthRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails = userAuthRepository.findByUsername(username).orElse(null);
    if (userDetails == null) {
      userDetails = userAuthRepository.findByEmail(username).orElse(null);
      if (userDetails == null) {
        throw new UserNotFoundException(username);
      }
    }
    return userDetails;
  }

  @Override
  public Boolean userExistsByUsername(String username) {
    return userAuthRepository.findByUsername(username).isPresent();
  }

  @Override
  public Boolean userExistsByEmail(String email) {
    return userAuthRepository.findByEmail(email).isPresent();
  }
}
