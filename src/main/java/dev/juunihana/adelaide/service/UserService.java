package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UpdateUserDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import dev.juunihana.adelaide.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserEntity getCurrentUserEntity();

  UserFullDto getFull();

  UserFullDto getCompact();

  void create(CreateUserDto createUserDto);

  void update(UpdateUserDto updateUserDto);

  void delete();

  String getCurrentUserEmail();

  boolean isUserNotAuthorized();
}
