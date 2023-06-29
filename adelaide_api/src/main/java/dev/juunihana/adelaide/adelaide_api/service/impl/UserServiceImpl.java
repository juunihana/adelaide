package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.configuration.CdnProperties;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.UpdateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserFullDTO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final CdnProperties cdnProperties;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseGet(() -> userRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException(username)));
  }

  @Override
  public UserAuthTokenDTO signIn(SignInDTO signInDTO) {
    return null;
  }

  @Override
  public boolean isUserSigned() {
    return isUserAuthorized();
  }

  @Override
  public UserEntity getSignedUserEntity() {
    return userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException(getCurrentUserUsername()));
  }

  @Override
  public UserEntity getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  @Override
  public UserEntity getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(email));
  }

  @Override
  public UserEntity getByPhone(String phone) {
    return userRepository.findByPhone(phone)
        .orElseThrow(() -> new UserNotFoundException(phone));
  }

  @Override
  public UserCompactDTO getSignedUser() {
    return userMapper.userToShortProfile(getSignedUserEntity());
  }

  @Override
  public UserFullDTO findUserProfileFull(String username) {
    System.out.println("Request full profile for user: " + username);

    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username));

    UserFullDTO userFullDTO = userMapper.userEntityToProfile(user);

    userFullDTO.setFriends(user.getFriends().stream()
        .map(userMapper::userToShortProfile)
        .collect(Collectors.toList()));

    return userFullDTO;
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
    System.out.println(
        "Request to upload avatar for user: " + getCurrentUserUsername());

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("image", avatar.getResource());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);

    String result = new RestTemplate().postForObject(
        cdnProperties.getUrl() + "new", entity, String.class);

    user.setAvatar(result);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void createUser(CreateUserProfileDTO createUserProfileDTO) {
    System.out.println("New user: " + createUserProfileDTO.toString());

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
    userEntity.setDateOfBirth(
        LocalDate.of(createUserProfileDTO.getYear(), createUserProfileDTO.getMonth(),
            createUserProfileDTO.getDay()));
    UserEntity user = userRepository.save(userEntity);

    passwordHistoryRepository.save(
        PasswordHistoryEntity.builder()
            .user(user)
            .passwordHash(password)
            .timeAdded(LocalDateTime.now())
            .build());
  }

  @Override
  public void changeUserInfo(UpdateUserProfileDTO updateUserProfileDTO) {
    System.out.println(
        "Update user {" + getCurrentUserUsername() + "}: " + updateUserProfileDTO.toString());

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    user.setEmail(updateUserProfileDTO.getEmail());
    user.setUsername(updateUserProfileDTO.getUsername());
    user.setDisplayName(updateUserProfileDTO.getDisplayName());
    user.setBio(updateUserProfileDTO.getBio());
    user.setPlace(updateUserProfileDTO.getPlace());
    user.setDateOfBirth(updateUserProfileDTO.getDateOfBirth());

    userRepository.save(user);
  }

  @Override
  public void deleteUser(String username) {
    System.out.println("Request to delete profile for user: " + username);

    if (!username.equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You cannot delete this user");
    }

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));
    user.setDeleted(true);

    userRepository.save(user);
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordDTO changePasswordDTO) {
    System.out.println(
        "Request to change password for user: " + getCurrentUserUsername());

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
  public List<UserCompactDTO> findUserFriends(String username) {
    System.out.println("Request to get list of friends for user: " + username);

    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("username " + username));

    return user.getFriends().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public List<UserCompactDTO> findIncomingFriendsRequests() {
    System.out.println(
        "Request to get list of incoming friends requests for user: " +
            getCurrentUserUsername());

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    if (!user.getUsername().equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You can't view this user's incoming friends requests");
    }

    return user.getIncomingFriendsRequests().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public List<UserCompactDTO> findOutgoingFriendsRequests() {
    System.out.println(
        "Request to get list of outgoing friends requests for user: " +
            getCurrentUserUsername());

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    if (!user.getUsername().equals(getCurrentUserUsername())) {
      throw new AccessDeniedException("You can't view this user's outgoing friends requests");
    }

    return user.getOutgoingFriendsRequests().stream()
        .map(userMapper::userToShortProfile)
        .toList();
  }

  @Override
  public void sendFriendRequest(String friendUsername) {
    System.out.println(
        "Send friends request, sender: {" + getCurrentUserUsername() +
            "} recipient: {" + friendUsername + "}");

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

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
  public void resolveFriendsRequest(String friendUsername, boolean accept) {
    System.out.println(
        "Accept friends request, sender: {" + getCurrentUserUsername() +
            "} recipient: {" + friendUsername + "}");

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    if (user.getIncomingFriendsRequests().contains(userFriend)) {
      if (accept) {
        user.getFriends().add(userFriend);
        userFriend.getFriends().add(user);
        user.getIncomingFriendsRequests().remove(userFriend);
        userFriend.getOutgoingFriendsRequests().remove(user);
      } else {
        user.getIncomingFriendsRequests().remove(userFriend);
      }

      userRepository.save(user);
      userRepository.save(userFriend);
    }
  }

  @Override
  public void removeFriend(String friendUsername) {
    System.out.println(
        "Remove friend, sender: {" + getCurrentUserUsername() +
            "} recipient: {" + friendUsername + "}");

    UserEntity user = userRepository.findByUsername(getCurrentUserUsername())
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

    UserEntity userFriend = userRepository.findByUsername(friendUsername)
        .orElseThrow(() -> new UserNotFoundException("username " + getCurrentUserUsername()));

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
