package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.UserApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;
  private final UserAuthService userAuthService;
  private final PostService postService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public SignedUserDTO getSignedUser() {
    return userService.getSignedUser();
  }

  @Override
  public UserProfileDTO getUserProfile(String username) {
    return userService.findUserByUsername(username);
  }

  @GetMapping("/user/post/{postId}")
  public PostDTO getPostById(@PathVariable String postId) {
    return postService.findById(postId);
  }

  @Override
  public SuccessCreateUserDTO createUser(CreateUserProfileDTO createUserProfileDTO) {
    System.out.println("New user: " + createUserProfileDTO.toString());
    return userService.save(createUserProfileDTO);
  }

  @Override
  @PreAuthorize("hasRole('ROLE_USER')")
  public SuccessPostDTO createPost(CreatePostDTO createPostDTO) {
    return postService.create(createPostDTO);
  }

  @Override
  public UserAuthTokenDTO signIn(SignInUserDTO signInUserDTO, HttpServletResponse response) {
    System.out.println("Signing in user: " + signInUserDTO.toString());

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          signInUserDTO.getUsername(), signInUserDTO.getPassword(), new ArrayList<>()));
      UserDetails userDetails = userAuthService.loadUserByUsername(signInUserDTO.getUsername());
      if (userDetails != null) {
        String token = jwtService.createToken(userDetails);
        Cookie cookie = new Cookie("auth", token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return UserAuthTokenDTO.builder().token(token).build();
      }
      throw new UserNotFoundException(signInUserDTO.getUsername());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new UserNotFoundException(signInUserDTO.getUsername());
    }
  }

  @Override
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> signOut() {
    System.out.println("Signing out user");
    return null;
  }
}
