package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.UserApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeEmailDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangeUsernameDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  /**
   * GET /profile/{username}/avatar POST /profile/avatar PUT /profile/avatar DELETE /profile/avatar
   * <p>
   * GET /profile/{username}/photos POST /profile/photos DELETE /profile/photos
   * <p>
   * complete UI for these features
   * <p>
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
  @GetMapping("/auth/signed")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public UserCompactDTO getSignedUser() {
    return userService.getSignedUser();
  }

  @Override
  @PostMapping("/auth/sign-in")
  public UserAuthTokenDTO signIn(
      @RequestBody @Valid SignInDTO signInDTO) {
    System.out.println("Signing in user: " + signInDTO.toString());

    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return UserAuthTokenDTO.builder()
        .token(jwtService.createToken(authentication))
        .build();
  }

  @Override
  @PostMapping("/auth/sign-out")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void signOut() {
    System.out.println("Signing out user");
  }

  @Override
  @PutMapping("/auth/email")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void changeEmail(
      @RequestBody @Valid ChangeEmailDTO changeEmailDTO) {
    userService.changeEmail(changeEmailDTO);
  }

  @Override
  @PutMapping("/auth/username")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void changeUsername(
      @RequestBody @Valid ChangeUsernameDTO changeUsernameDTO) {
    userService.changeUsername(changeUsernameDTO);
  }

  @Override
  @PutMapping("/auth/password")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void changePassword(
      @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
    userService.changePassword(changePasswordDTO);
  }

  @Override
  @GetMapping("/profile/{username}")
  public UserFullDTO getUserProfile(
      @PathVariable String username) {
    return userService.findUserProfile(username);
  }

  @Override
  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(
      @RequestBody @Valid CreateUserProfileDTO createUserProfileDTO) {
    System.out.println("New user: " + createUserProfileDTO.toString());
    userService.createUser(createUserProfileDTO);
  }

  @PostMapping("/avatar")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  public void uploadAvatar(@RequestParam MultipartFile image) {
    userService.uploadAvatar(image);
  }

  @Override
  @PutMapping("/profile/{username}")
  public void updateUser(
      @PathVariable String username,
      @RequestBody @Valid ChangeUserProfileDTO changeUserProfileDTO) {
    System.out.println("Update user: " + changeUserProfileDTO.toString());
    userService.changeUserInfo(changeUserProfileDTO);
  }

  @Override
  @DeleteMapping("/profile/{username}")
  public void deleteUser(
      @PathVariable String username) {
    userService.deleteUser(username);
  }

  @GetMapping("/friends/{username}")
  public List<UserCompactDTO> getUserFriends(
      @PathVariable String username) {
    return userService.findUserFriends(username);
  }

  @GetMapping("/friends/in")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public List<UserCompactDTO> getUserFriendsIncoming() {
    return userService.findIncomingFriendsRequests();
  }

  @GetMapping("/friends/out")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public List<UserCompactDTO> getUserFriendsOutgoing() {
    return userService.findOutgoingFriendsRequests();
  }

  @PostMapping("/friends/{friendUsername}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void sendFriendsRequest(
      @PathVariable String friendUsername) {
    userService.sendFriendRequest(friendUsername);
  }

  @PutMapping("/friends/{friendUsername}/accept")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void acceptFriend(
      @PathVariable String friendUsername) {
    userService.acceptFriend(friendUsername);
  }

  @PutMapping("/friends/{friendUsername}/decline")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void declineFriend(
      @PathVariable String friendUsername) {
    userService.declineFriend(friendUsername);
  }

  @DeleteMapping("/friends/{friendUsername}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void removeFriend(
      @PathVariable String friendUsername) {
    userService.removeFriend(friendUsername);
  }
}
