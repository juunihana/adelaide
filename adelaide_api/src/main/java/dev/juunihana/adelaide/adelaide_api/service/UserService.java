package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;

public interface UserService {

  UserProfileDTO findUserByUsername(String username);

  UserProfileDTO findUserByEmail(String email);

  Boolean userExistsByUsername(String username);

  Boolean userExistsByEmail(String email);

  Boolean userExistsByPhone(String phone);

  SuccessCreateUserDTO save(CreateUserProfileDTO createUserProfileDTO);
}
