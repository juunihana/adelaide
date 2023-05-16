package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.UserApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  /**
   * todo (this week)
   * PUT /auth/username - change username
   * PUT /profile - change user profile info
   * DELETE /profile - delete user profile
   *
   * GET /profile/{username}/avatar
   * POST /profile/avatar
   * PUT /profile/avatar
   * DELETE /profile/avatar
   *
   * GET /friends/{username} - get user's friends
   * GET /friends/{username}/in - get incoming friends requests
   * GET /friends/{username}/out - get outgoing friends requests
   * POST /friends - send outgoing request
   * PUT /friends/{request_id} - accept or decline incoming request
   * DELETE /friends - remove from friends list
   *
   * GET /profile/{username}/photos
   * POST /profile/photos
   * DELETE /profile/photos
   *
   * also groups controller and five tables:
   * users_avatars
   * groups
   * users_in_groups
   * users_friends
   * users_friends_requests
   *
   * complete UI for these features
   *
   * todo (next week)
   * messages api
   * post feed
   * posts search
   * user page posts filter
   *
   * todo (long run)
   * email verification
   * user privacy settings
   */

  @Override
  @GetMapping("/auth/signed")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public SignedUserDTO getSignedUser() {
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
  public ResponseEntity<?> signOut() {
    System.out.println("Signing out user");
    return null;
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
  public UserProfileDTO getUserProfile(
      @PathVariable String username) {
    return userService.findUserProfile(username);
  }

  @Override
  @PostMapping("/new")
  public SuccessCreateUserDTO createUser(
      @RequestBody @Valid CreateUserProfileDTO createUserProfileDTO) {
    System.out.println("New user: " + createUserProfileDTO.toString());
    return userService.signUp(createUserProfileDTO);
  }
}
