package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PasswordHistoryEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.exception.PasswordPreviouslyUsedException;
import dev.juunihana.adelaide.adelaide_api.exception.PasswordsDoesNotMatchException;
import dev.juunihana.adelaide.adelaide_api.exception.UserAlreadyExistsException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.UserMapper;
import dev.juunihana.adelaide.adelaide_api.repository.PasswordHistoryRepository;
import dev.juunihana.adelaide.adelaide_api.repository.UserRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordHistoryRepository passwordHistoryRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public SignedUserDTO getSignedUser() {
    return userMapper.userToSigned(
        userRepository.findByUsername(getCurrentUserUsername())
            .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername())));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails = userRepository.findByUsername(username).orElse(null);
    if (Objects.nonNull(userDetails)) {
      return userDetails;
    } else {
      userDetails = userRepository.findByEmail(username).orElse(null);
      if (Objects.nonNull(userDetails)) {
        return userDetails;
      }
    }

    throw new UserNotFoundException(username);
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
    String password = passwordEncoder.encode(createUserProfileDTO.getPassword());

    UserEntity userEntity = userMapper.createUserToEntity(createUserProfileDTO);
    userEntity.setId(userId);
    userEntity.setTimeJoined(LocalDateTime.now());
    userEntity.setPassword(password);
    userRepository.save(userEntity);

    passwordHistoryRepository.save(
        PasswordHistoryEntity.builder()
            .passwordHash(password)
            .timeAdded(LocalDateTime.now())
            .build());

    return SuccessCreateUserDTO.builder()
        .username(userEntity.getUsername())
        .build();
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordDTO changePasswordDTO) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (passwordHistoryRepository.findAllByUser(user).isEmpty()) {
      if (passwordEncoder.encode(changePasswordDTO.getCurrentPassword())
          .equals(user.getPassword())) {
        String password = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(password);
        userRepository.save(user);
        passwordHistoryRepository.save(PasswordHistoryEntity.builder()
            .user(user)
            .passwordHash(password)
            .timeAdded(LocalDateTime.now())
            .build());
      } else {
        throw new PasswordsDoesNotMatchException();
      }
    } else {
      throw new PasswordPreviouslyUsedException();
    }
  }

  private String getCurrentUserUsername() {
    if (!isUserAuthorized()) {
      return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
  }

  private boolean isUserAuthorized() {
    return !SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal().equals("anonymousUser");
  }
}
