package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.repository.UserAuthRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

//  private final UserAuthRepository userAuthRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserAuthEntity create(CreateUserProfileDTO createUserProfileDTO) {
    UUID userId = UUID.randomUUID();
    UserAuthEntity userAuth = UserAuthEntity.builder()
        .id(userId)
        .username(createUserProfileDTO.getUsername())
        .email(createUserProfileDTO.getEmail())
        .password(passwordEncoder.encode(createUserProfileDTO.getPassword()))
        .build();

    return userAuth;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userAuthRepository.findByUsername(username)
        .orElse(userAuthRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException(username)));
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
