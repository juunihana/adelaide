package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.user.UserProfileDTO;

public interface UserService {

  UserProfileDTO findUserByUsername(String username);
}
