package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeEmailDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUsernameDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.ShortUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  ShortUserProfileDTO getSignedUser();

  @Override
  UserDetails loadUserByUsername(String username);

  UserProfileDTO findUserProfile(String username);

  Boolean userExistsByUsername(String username);

  Boolean userExistsByEmail(String email);

  Boolean userExistsByPhone(String phone);

  void createUser(CreateUserProfileDTO createUserProfileDTO);

  void changeUserInfo(ChangeUserProfileDTO changeUserProfileDTO);

  void deleteUser(String username);

  void changeEmail(ChangeEmailDTO changeEmailDTO);

  void changeUsername(ChangeUsernameDTO changeUsernameDTO);

  void changePassword(ChangePasswordDTO changePasswordDTO);

  List<ShortUserProfileDTO> findUserFriends(String username);
  void acceptFriend(String friendUsername);

  void declineFriend(String friendUsername);
}
