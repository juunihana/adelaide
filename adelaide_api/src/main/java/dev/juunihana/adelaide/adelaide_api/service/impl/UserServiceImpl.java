package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeEmailDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUsernameDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.ShortUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PasswordHistoryEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.PasswordPreviouslyUsedException;
import dev.juunihana.adelaide.adelaide_api.exception.PasswordsDoesNotMatchException;
import dev.juunihana.adelaide.adelaide_api.exception.UserAlreadyExistsException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.UserMapper;
import dev.juunihana.adelaide.adelaide_api.repository.PasswordHistoryRepository;
import dev.juunihana.adelaide.adelaide_api.repository.UserRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordHistoryRepository passwordHistoryRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ShortUserProfileDTO getSignedUser() {
    return userMapper.userToShortProfile(
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

  public void uploadAvatar(MultipartFile avatar) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("image", avatar.getResource());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);

    String result = new RestTemplate().postForObject(
        "http://localhost:8081/images/new",entity, String.class);

    user.setAvatar("http://localhost:8081/images/" + result);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void createUser(CreateUserProfileDTO createUserProfileDTO) {
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
    UserEntity user = userRepository.save(userEntity);

    passwordHistoryRepository.save(
        PasswordHistoryEntity.builder()
            .user(user)
            .passwordHash(password)
            .timeAdded(LocalDateTime.now())
            .build());
  }

  @Override
  public void changeUserInfo(ChangeUserProfileDTO changeUserProfileDTO) {
    if (StringUtils.hasLength(changeUserProfileDTO.getPhone()) &&
        userExistsByPhone(changeUserProfileDTO.getPhone())) {
      throw new UserAlreadyExistsException("phone " + changeUserProfileDTO.getPhone());
    }

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (StringUtils.hasLength(changeUserProfileDTO.getFirstName())) {
      user.setFirstName(changeUserProfileDTO.getFirstName());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getLastName())) {
      user.setLastName(changeUserProfileDTO.getLastName());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getMiddleName())) {
      user.setMiddleName(changeUserProfileDTO.getMiddleName());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getMaidenSurname())) {
      user.setMaidenSurname(changeUserProfileDTO.getMaidenSurname());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getBio())) {
      user.setBio(changeUserProfileDTO.getBio());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getPlace())) {
      user.setPlace(changeUserProfileDTO.getPlace());
    }
    if (StringUtils.hasLength(changeUserProfileDTO.getPhone())) {
      user.setPhone(changeUserProfileDTO.getPhone());
    }
    if (changeUserProfileDTO.getDateOfBirth() != null) {
      user.setDateOfBirth(changeUserProfileDTO.getDateOfBirth());
    }

    userRepository.save(user);
  }

  @Override
  public void deleteUser(String username) {
    if (!username.equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You cannot delete this user");
    }

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));
    user.setDeleted(true);

    userRepository.save(user);
  }

  @Override
  @Transactional
  public void changeEmail(ChangeEmailDTO changeEmailDTO) {
    if (userRepository.findByUsername(changeEmailDTO.getNewEmail()).isPresent()) {
      throw new UserAlreadyExistsException("email " + changeEmailDTO.getNewEmail());
    }

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (passwordEncoder.encode(changeEmailDTO.getPassword())
        .equals(user.getPassword())) {
      user.setEmail(changeEmailDTO.getNewEmail());
      userRepository.save(user);
    } else {
      throw new PasswordsDoesNotMatchException();
    }
  }

  @Override
  @Transactional
  public void changeUsername(ChangeUsernameDTO changeUsernameDTO) {
    if (userRepository.findByUsername(changeUsernameDTO.getNewUsername()).isPresent()) {
      throw new UserAlreadyExistsException("username " + changeUsernameDTO.getNewUsername());
    }

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (passwordEncoder.encode(changeUsernameDTO.getPassword())
        .equals(user.getPassword())) {
      user.setUsername(changeUsernameDTO.getNewUsername());
      userRepository.save(user);
    } else {
      throw new PasswordsDoesNotMatchException();
    }
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordDTO changePasswordDTO) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (passwordEncoder.encode(changePasswordDTO.getCurrentPassword())
        .equals(user.getPassword())) {
      String password = passwordEncoder.encode(changePasswordDTO.getNewPassword());

      if (passwordHistoryRepository.findAllByUserAndPasswordHash(user, password).isEmpty()) {
        user.setPassword(password);
        userRepository.save(user);
        passwordHistoryRepository.save(PasswordHistoryEntity.builder()
            .user(user)
            .passwordHash(password)
            .timeAdded(LocalDateTime.now())
            .build());
      } else {
        throw new PasswordPreviouslyUsedException();
      }
    } else {
      throw new PasswordsDoesNotMatchException();
    }
  }

  @Override
  public List<ShortUserProfileDTO> findUserFriends(String username) {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return user.getFriends().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public List<ShortUserProfileDTO> findIncomingFriendsRequests() {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (!user.getUsername().equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You can't view this user's incoming friends requests");
    }

    return user.getIncomingFriendsRequests().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public List<ShortUserProfileDTO> findOutgoingFriendsRequests() {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (!user.getUsername().equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You can't view this user's outgoing friends requests");
    }

    return user.getOutgoingFriendsRequests().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public void sendFriendRequest(String friendUsername) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (user.getUsername().equals(friendUsername)) {
      throw new AccessDeniedException("You can't send friend requests to yourself");
    }

    if (!user.getOutgoingFriendsRequests().contains(userFriend)) {
      userFriend.getIncomingFriendsRequests().add(user);

      userRepository.save(user);
      userRepository.save(userFriend);
    }
  }

  @Override
  @Transactional
  public void acceptFriend(String friendUsername) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (user.getIncomingFriendsRequests().contains(userFriend)) {
      user.getFriends().add(userFriend);
      userFriend.getFriends().add(user);
      user.getIncomingFriendsRequests().remove(userFriend);
      userFriend.getOutgoingFriendsRequests().remove(user);

      userRepository.save(user);
      userRepository.save(userFriend);
    }
  }

  @Override
  @Transactional
  public void declineFriend(String friendUsername) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (user.getIncomingFriendsRequests().contains(userFriend)) {
      user.getIncomingFriendsRequests().remove(userFriend);

      userRepository.save(user);
      userRepository.save(userFriend);
    }
  }

  @Override
  public void removeFriend(String friendUsername) {
    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));

    if (user.getFriends().contains(userFriend)) {
      user.getFriends().remove(userFriend);
      userFriend.getFriends().remove(user);

      userRepository.save(user);
      userRepository.save(userFriend);
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
