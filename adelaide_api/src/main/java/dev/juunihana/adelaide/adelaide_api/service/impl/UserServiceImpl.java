package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.exception.UserAlreadyExistsException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.UserMapper;
import dev.juunihana.adelaide.adelaide_api.repository.UserRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public SignedUserDTO getSignedUser() {
    return SignedUserDTO.builder()
        .username(((UserEntity) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal()).getUsername())
        .build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElse(userRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException(username)));
  }

  @Override
  public UserProfileDTO findUserProfile(String username) {
    return userMapper.userEntityToProfile(userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username)));
  }

  @Override
  public Boolean userExistsByUsername(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  @Override
  public Boolean userExistsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public Boolean userExistsByPhone(String phone) {
    return userRepository.findByPhone(phone).isPresent();
  }

  @Override
  @Transactional
  public SuccessCreateUserDTO signUp(CreateUserProfileDTO createUserProfileDTO) {
    if (userExistsByUsername(createUserProfileDTO.getUsername())) {
      throw new UserAlreadyExistsException("username " + createUserProfileDTO.getUsername());
    }
    if (userExistsByEmail(createUserProfileDTO.getEmail())) {
      throw new UserAlreadyExistsException("email " + createUserProfileDTO.getEmail());
    }
    if (StringUtils.hasLength(createUserProfileDTO.getPhone()) &&
        userExistsByPhone(createUserProfileDTO.getPhone())) {
      throw new UserAlreadyExistsException("phone " + createUserProfileDTO.getPhone());
    }

    UUID userId = UUID.randomUUID();

    UserEntity userEntity = userMapper.createUserToEntity(createUserProfileDTO);
    userEntity.setId(userId);
    userEntity.setTimeJoined(LocalDateTime.now());

    userRepository.save(userEntity);

    return SuccessCreateUserDTO.builder()
        .username(userEntity.getUsername())
        .build();
  }
}
