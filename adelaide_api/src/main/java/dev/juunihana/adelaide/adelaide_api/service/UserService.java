package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SignedUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.SuccessCreateUserDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  SignedUserDTO getSignedUser();

  UserEntity findByUsername(String username);

  UserProfileDTO findUserProfile(String username);

  Boolean userExistsByPhone(String phone);

  SuccessCreateUserDTO signUp(CreateUserProfileDTO createUserProfileDTO);
}
