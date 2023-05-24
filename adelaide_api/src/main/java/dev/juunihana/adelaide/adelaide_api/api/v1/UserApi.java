package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeEmailDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUsernameDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.ShortUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;

public interface UserApi {

  ShortUserProfileDTO getSignedUser();

  UserProfileDTO getUserProfile(String username);

  void createUser(CreateUserProfileDTO createUserProfileDTO);

  void updateUser(String username,ChangeUserProfileDTO changeUserProfileDTO);

  UserAuthTokenDTO signIn(SignInDTO signInDTO);

  void signOut();

  void changeEmail(ChangeEmailDTO changeEmailDTO);

  void changeUsername(ChangeUsernameDTO changeUsernameDTO);

  void changePassword(ChangePasswordDTO changePasswordDTO);

  void deleteUser(String username);
}
