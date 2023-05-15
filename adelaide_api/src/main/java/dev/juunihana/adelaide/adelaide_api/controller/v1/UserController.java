package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  @GetMapping("/auth/signed")
  public SignedUserDTO getSignedUser() {
    return userService.getSignedUser();
  }

  @PostMapping("/auth/sign-in")
  public UserAuthTokenDTO signIn(SignInDTO signInDTO) {
    System.out.println("Signing in user: " + signInDTO.toString());

    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return UserAuthTokenDTO.builder()
        .token(jwtService.createToken(authentication))
        .build();
  }

  @PostMapping("/auth/sign-out")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> signOut() {
    System.out.println("Signing out user");
    return null;
  }

  @GetMapping("/profile/{username}")
  public UserProfileDTO getUserProfile(
      @PathVariable String username) {
    return userService.findUserProfile(username);
  }

  public SuccessCreateUserDTO createUser(CreateUserProfileDTO createUserProfileDTO) {
    System.out.println("New user: " + createUserProfileDTO.toString());
    return userService.signUp(createUserProfileDTO);
  }
}
