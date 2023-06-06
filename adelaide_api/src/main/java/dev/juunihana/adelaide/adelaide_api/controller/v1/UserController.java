package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.UserApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CheckUsernameEmailDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.UpdateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserFullDTO;
import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  /**
   * todo (next week)
   * messages api
   * post feed
   * posts search
   * user page posts filter
   * <p>
   * todo (long run)
   * email verification
   * user privacy settings
   */

  @Override
  public UserCompactDTO getSignedUser() {
    return userService.getSignedUser();
  }

  @Override
  public UserAuthTokenDTO signIn(SignInDTO signInDTO) {
    System.out.println("Signing in user: " + signInDTO.getUsername());

    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    System.out.println("Signing in user: " + signInDTO.getUsername() + ". Success!");

    return UserAuthTokenDTO.builder()
        .token(jwtService.createToken(authentication))
        .build();
  }

  @Override
  public UserFullDTO getUserProfile(String username) {
    return userService.findUserProfileFull(username);
  }

  @Override
  public void create(CreateUserProfileDTO createUserProfileDTO) {
    userService.createUser(createUserProfileDTO);
  }

  @Override
  public void checkUsernameEmail(CheckUsernameEmailDTO dto) {
    userService.userExistsByUsername(dto.getUsername());
    userService.userExistsByEmail(dto.getEmail());
  }

  public void uploadAvatar(MultipartFile image) {
    userService.uploadAvatar(image);
  }

  @Override
  public void updatePassword(ChangePasswordDTO changePasswordDTO) {
    userService.changePassword(changePasswordDTO);
  }

  @Override
  public void update(String username, UpdateUserProfileDTO updateUserProfileDTO) {
    userService.changeUserInfo(updateUserProfileDTO);
  }


  @Override
  public void delete(String username) {
    userService.deleteUser(username);
  }

  @Override
  public List<UserCompactDTO> getUserFriends(String username) {
    return userService.findUserFriends(username);
  }

  @Override
  public List<UserCompactDTO> getUserFriendsIncoming() {
    return userService.findIncomingFriendsRequests();
  }

  @Override
  public List<UserCompactDTO> getUserFriendsOutgoing() {
    return userService.findOutgoingFriendsRequests();
  }

  @Override
  public void sendFriendsRequest(String friendUsername) {
    userService.sendFriendRequest(friendUsername);
  }

  @Override
  public void resolveFriendsRequest(String friendUsername, boolean accept) {
    userService.resolveFriendsRequest(friendUsername, accept);
  }

  @Override
  public void removeFriend(String friendUsername) {
    userService.removeFriend(friendUsername);
  }
}
