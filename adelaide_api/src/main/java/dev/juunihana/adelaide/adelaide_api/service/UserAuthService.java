package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserAuthService extends UserDetailsService {

  UserAuthEntity create(CreateUserProfileDTO createUserProfileDTO);

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  Boolean userExistsByUsername(String username);

  Boolean userExistsByEmail(String email);
}
