package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
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
import java.util.List;
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
  private final UserMapper userMapper;
  private final UserAuthService userAuthService;

  @Override
  public SignedUserDTO getSignedUser() {
    return SignedUserDTO.builder()
        .username(((UserAuthEntity) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal()).getUsername())
        .build();
  }

  @Override
  public UserEntity findByUsername(String username) {
    return userRepository.findByUserAuthUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username));
  }

  @Override
  public UserProfileDTO findUserProfile(String username) {
    return userMapper.userEntityToProfile(userRepository.findByUserAuthUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username)));
  }

  @Override
  public Boolean userExistsByPhone(String phone) {
    return userRepository.findByPhone(phone).isPresent();
  }

  @Override
  @Transactional
  public SuccessCreateUserDTO signUp(CreateUserProfileDTO createUserProfileDTO) {
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

    UserAuthEntity userAuth = userAuthService.create(createUserProfileDTO);

    UserEntity userEntity = userMapper.createUserToEntity(createUserProfileDTO);
    userEntity.setId(userAuth.getId());
    userEntity.setUserAuth(userAuth);
    userEntity.setTimeJoined(LocalDateTime.now());

    userRepository.save(userEntity);

    return SuccessCreateUserDTO.builder()
        .username(userAuth.getUsername())
        .build();
  }
}
