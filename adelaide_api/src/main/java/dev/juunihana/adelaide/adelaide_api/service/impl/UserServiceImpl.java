package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.exception.UserAlreadyExistsException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.UserMapper;
import dev.juunihana.adelaide.adelaide_api.repository.UserAuthRepository;
import dev.juunihana.adelaide.adelaide_api.repository.UserRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserAuthRepository userAuthRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserAuthService userAuthService;

  @Override
  public SignedUserDTO getSignedUser() {
    return SignedUserDTO.builder()
        .username(((UserAuthEntity) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal()).getUsername())
        .build();
  }

  @Override
  public UserProfileDTO findUserByUsername(String username) {
    UUID userId = userAuthRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username)).getId();

    return userMapper.userEntityToProfile(userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("username " + username)));
  }

  @Override
  public Boolean userExistsByPhone(String phone) {
    return userRepository.findByPhone(phone).isPresent();
  }

  @Override
  @Transactional
  public SuccessCreateUserDTO save(CreateUserProfileDTO createUserProfileDTO) {
    if (userAuthService.userExistsByUsername(createUserProfileDTO.getUsername())) {
      throw new UserAlreadyExistsException("username " + createUserProfileDTO.getUsername());
    }
    if (userAuthService.userExistsByEmail(createUserProfileDTO.getEmail())) {
      throw new UserAlreadyExistsException("email " + createUserProfileDTO.getEmail());
    }
    if (StringUtils.hasLength(createUserProfileDTO.getPhone()) &&
        userExistsByPhone(createUserProfileDTO.getPhone())) {
      throw new UserAlreadyExistsException("phone " + createUserProfileDTO.getPhone());
    }

    UUID userId = UUID.randomUUID();

    UserAuthEntity userAuth = UserAuthEntity.builder()
        .id(userId)
        .username(createUserProfileDTO.getUsername())
        .email(createUserProfileDTO.getEmail())
        .password(passwordEncoder.encode(createUserProfileDTO.getPassword()))
        .build();

    UserEntity userEntity = userMapper.createUserToEntity(createUserProfileDTO);
    userEntity.setId(userId);
    userEntity.setUserAuth(userAuth);
    userEntity.setTimeJoined(LocalDateTime.now());

    userAuthRepository.save(userAuth);
    userRepository.save(userEntity);

    return SuccessCreateUserDTO.builder()
        .result("userNewSuccess")
        .username(userAuth.getUsername())
        .build();
  }
}
