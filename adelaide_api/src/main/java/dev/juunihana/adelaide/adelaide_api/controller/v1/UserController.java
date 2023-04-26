package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.UserApi;
import dev.juunihana.adelaide.adelaide_api.dto.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UserApi {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }
  public UserProfileDTO getUserProfile(String username) {
    return userService.findUserByUsername(username);
  }
}
