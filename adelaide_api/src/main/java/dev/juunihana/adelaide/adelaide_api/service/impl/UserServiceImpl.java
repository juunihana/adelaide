package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserProfileDTO findUserByUsername(String username) {
    return userMapper.userEntityToProfile(userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username)));
  }

  @Override
  public UserProfileDTO findUserByEmail(String email) {
    return userMapper.userEntityToProfile(userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("email " + email)));
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
  public SuccessCreateUserDTO save(CreateUserProfileDTO createUserProfileDTO) {
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
    UserEntity userEntity = userMapper.createUserToEntity(createUserProfileDTO);
    userEntity.setTimeJoined(LocalDateTime.now());

    UserEntity user = userRepository.save(userEntity);

    return SuccessCreateUserDTO.builder()
        .result("userNewSuccess")
        .username(user.getUsername())
        .build();
  }
}
