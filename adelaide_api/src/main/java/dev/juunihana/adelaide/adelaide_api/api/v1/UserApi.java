package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.ChangePasswordDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.SignInDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserAuthTokenDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import org.springframework.http.ResponseEntity;

public interface UserApi {

  //  @GetMapping("/user-signed")
  SignedUserDTO getSignedUser();

  //  @GetMapping("/user/{username}")
  UserProfileDTO getUserProfile(String username);

  //  @PostMapping("/user/new")
//  @ResponseStatus(HttpStatus.CREATED)
  SuccessCreateUserDTO createUser(CreateUserProfileDTO createUserProfileDTO);

  //  @PostMapping("/user/sign-in")
  UserAuthTokenDTO signIn(SignInDTO signInDTO);

  //  @PostMapping("/user/sign-out")
  ResponseEntity<?> signOut();

  void changePassword(ChangePasswordDTO changePasswordDTO);
}
