package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.UpdateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserFullDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {

  boolean isUserSigned();

  UserEntity getSignedUserEntity();

  UserEntity getByUsername(String username);

  UserEntity getByEmail(String email);

  UserEntity getByPhone(String phone);

  UserCompactDTO getSignedUser();

  @Override
  UserDetails loadUserByUsername(String username);

  UserAuthTokenDTO signIn(SignInDTO signInDTO);

  UserFullDTO findUserProfileFull(String username);

  Boolean userExistsByUsername(String username);

  Boolean userExistsByEmail(String email);

  Boolean userExistsByPhone(String phone);

  void uploadAvatar(MultipartFile avatar);

  void createUser(CreateUserProfileDTO createUserProfileDTO);

  void changeUserInfo(UpdateUserProfileDTO updateUserProfileDTO);

  void deleteUser(String username);

  void changePassword(ChangePasswordDTO changePasswordDTO);

  List<UserCompactDTO> findUserFriends(String username);

  List<UserCompactDTO> findIncomingFriendsRequests();

  List<UserCompactDTO> findOutgoingFriendsRequests();

  void sendFriendRequest(String friendUsername);

  void resolveFriendsRequest(String friendUsername, boolean accept);

  void removeFriend(String friendUsername);
}
