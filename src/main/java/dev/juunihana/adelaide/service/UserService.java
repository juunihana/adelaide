package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserFullDto getFull();

  UserFullDto getCompact();

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);

  void create(CreateUserDto createUserDto);

  void update(CreateUserDto createUserDto);
}
