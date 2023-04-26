package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.UserMapper;
import dev.juunihana.adelaide.adelaide_api.repository.UserRepository;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserServiceImpl(
      UserRepository userRepository,
      UserMapper userMapper
  ) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public UserProfileDTO findUserByUsername(String username) {
    return userMapper.userEntityToProfile(userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username)));
  }
}
