package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserApi {

  @GetMapping("/user/{username}")
  UserProfileDTO getUserProfile(@PathVariable String username);

  @PostMapping("/user/new")
  @ResponseStatus(HttpStatus.CREATED)
  SuccessCreateUserDTO createUser(@RequestBody @Valid CreateUserProfileDTO createUserProfileDTO);

  @PostMapping("/user/sign-in")
  UserAuthTokenDTO signIn(@RequestBody @Valid SignInUserDTO signInUserDTO,
      HttpServletResponse response);

  @PostMapping("/user/sign-out")
  ResponseEntity<?> signOut();
}
