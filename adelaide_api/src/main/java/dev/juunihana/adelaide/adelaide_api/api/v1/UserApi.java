package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.user.UserProfileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserApi {

  @GetMapping("/user/{username}")
  UserProfileDTO getUserProfile(@PathVariable String username);
}
