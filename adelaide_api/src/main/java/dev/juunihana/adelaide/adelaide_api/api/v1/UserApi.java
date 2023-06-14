package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.PreCheckSignUpDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.UpdateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserFullDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequestMapping("/api/v1/users")
public interface UserApi {

  @GetMapping("/auth/signed")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  UserCompactDTO getSignedUser();

  @PostMapping("/auth/sign-in")
  UserAuthTokenDTO signIn(@RequestBody @Valid SignInDTO dto);

  @GetMapping("/profile/{username}")
  UserFullDTO getUserProfile(@PathVariable String username);

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  void create(@RequestBody @Valid CreateUserProfileDTO dto);

  @PostMapping("/check/username-email")
  void preCheckSignUp(@RequestBody @Valid PreCheckSignUpDTO dto);

  @PutMapping("/profile/{username}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void update(@PathVariable String username, @RequestBody @Valid UpdateUserProfileDTO dto);

  @DeleteMapping("/profile/{username}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void delete(@PathVariable String username);

  @PostMapping("/avatar")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  void uploadAvatar(@RequestParam MultipartFile avatar);

  @PutMapping("/auth/password")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void updatePassword(@RequestBody @Valid ChangePasswordDTO dto);

  @GetMapping("/friends/{username}")
  List<UserCompactDTO> getUserFriends(@PathVariable String username);

  @GetMapping("/friends/in")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  List<UserCompactDTO> getUserFriendsIncoming();

  @GetMapping("/friends/out")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  List<UserCompactDTO> getUserFriendsOutgoing();

  @PostMapping("/friends/{friendUsername}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void sendFriendsRequest(
      @PathVariable String friendUsername);

  @PutMapping("/friends/{friendUsername}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void resolveFriendsRequest(@PathVariable String friendUsername, @RequestParam boolean accept);

  @DeleteMapping("/friends/{friendUsername}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void removeFriend(@PathVariable String friendUsername);
}
