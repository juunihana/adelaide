package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;

public interface UserService {

  SignedUserDTO getSignedUser();

  UserProfileDTO findUserByUsername(String username);

  Boolean userExistsByPhone(String phone);

  SuccessCreateUserDTO save(CreateUserProfileDTO createUserProfileDTO);
}
