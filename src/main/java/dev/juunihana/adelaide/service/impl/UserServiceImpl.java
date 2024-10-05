package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import dev.juunihana.adelaide.entity.UserEntity;
import dev.juunihana.adelaide.exception.NotFoundException;
import dev.juunihana.adelaide.exception.UserAlreadyExistsByEmailException;
import dev.juunihana.adelaide.exception.UserAlreadyExistsByPhoneException;
import dev.juunihana.adelaide.mapper.UserMapper;
import dev.juunihana.adelaide.repository.UserRepository;
import dev.juunihana.adelaide.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
    return userRepository.findByEmail(emailOrPhone)
        .orElseGet(() -> userRepository.findByPhone(emailOrPhone)
            .orElseThrow(() -> new NotFoundException(emailOrPhone)));
  }

  @Override
  public UserFullDto getFull() {
    return null;
  }

  @Override
  public UserFullDto getCompact() {
    return null;
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public boolean existsByPhone(String phone) {
    return userRepository.findByPhone(phone).isPresent();
  }

  @Override
  @Transactional
  public void create(CreateUserDto createUserDto) {
    if (existsByEmail(createUserDto.getEmail())) {
      throw new UserAlreadyExistsByEmailException(
          "User with email " + createUserDto.getEmail() + " already exists");
    }

    if (existsByPhone(createUserDto.getPhone())) {
      throw new UserAlreadyExistsByPhoneException(
          "User with phone " + createUserDto.getPhone() + " already exists");
    }

    UserEntity entity = userMapper.createUserToEntity(createUserDto);

    entity.setPassword(encoder.encode(createUserDto.getPassword()));
    entity.setCreateTime(LocalDateTime.now());
    entity.setUpdateTime(LocalDateTime.now());

    userRepository.save(entity);
  }

  @Override
  public void update(CreateUserDto createUserDto) {
  }
}
