package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UpdateUserDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import dev.juunihana.adelaide.entity.UserEntity;
import dev.juunihana.adelaide.exception.BadRequestException;
import dev.juunihana.adelaide.exception.NotAuthorizedException;
import dev.juunihana.adelaide.exception.NotFoundException;
import dev.juunihana.adelaide.mapper.UserMapper;
import dev.juunihana.adelaide.repository.UserRepository;
import dev.juunihana.adelaide.service.UserService;
import dev.juunihana.adelaide.util.Errors;
import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(
            () -> new NotFoundException(MessageFormat.format(Errors.USER_NOT_FOUND_EMAIL, email)));
  }

  @Override
  public UserEntity getCurrentUserEntity() {
    return userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(
        () -> new NotFoundException(MessageFormat.format(Errors.USER_NOT_FOUND_EMAIL, getCurrentUserEmail())));
  }

  @Override
  public UserFullDto getFull() {
    return null;
  }

  @Override
  public UserFullDto getCompact() {
    return null;
  }

  private boolean existsByEmail(String email) {
    return (userRepository.findByEmail(email).isPresent());
  }

  private boolean existsByPhone(String phone) {
    return (userRepository.findByPhone(phone).isPresent());
  }

  @Override
  @Transactional
  public void create(CreateUserDto createUserDto) {
    if (existsByEmail(createUserDto.getEmail())) {
      throw new BadRequestException(
          MessageFormat.format(Errors.USER_EXISTS_EMAIL, createUserDto.getEmail()));
    }

    if (existsByPhone(createUserDto.getPhone())) {
      throw new BadRequestException(
          MessageFormat.format(Errors.USER_EXISTS_PHONE, createUserDto.getPhone()));
    }

    UserEntity entity = userMapper.createUserToEntity(createUserDto);

    entity.setPassword(encoder.encode(createUserDto.getPassword()));
    entity.setCreateTime(LocalDateTime.now());
    entity.setUpdateTime(LocalDateTime.now());

    userRepository.save(entity);
  }

  @Override
  public void update(UpdateUserDto updateUserDto) {
    if (isUserNotAuthorized()) {
      throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
    }

    UserEntity entity = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(
        () -> new NotFoundException(
            MessageFormat.format(Errors.USER_NOT_FOUND_EMAIL, getCurrentUserEmail())));

    if (!updateUserDto.getEmail().equals(entity.getEmail()) && existsByEmail(
        updateUserDto.getEmail())) {
      throw new BadRequestException(
          MessageFormat.format(Errors.USER_EXISTS_EMAIL, updateUserDto.getEmail()));
    }

    if (!updateUserDto.getPhone().equals(entity.getPhone()) && existsByPhone(
        updateUserDto.getPhone())) {
      throw new BadRequestException(
          MessageFormat.format(Errors.USER_EXISTS_PHONE, updateUserDto.getPhone()));
    }

    UUID userId = entity.getId();
    LocalDateTime createTime = entity.getCreateTime();
    entity = userMapper.updateUserToEntity(updateUserDto);
    entity.setId(userId);
    entity.setCreateTime(createTime);
    entity.setUpdateTime(LocalDateTime.now());

    userRepository.save(entity);
  }

  @Override
  public void delete() {
    if (isUserNotAuthorized()) {
      throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
    }

    UserEntity entity = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(
        () -> new NotFoundException(
            MessageFormat.format(Errors.USER_NOT_FOUND_EMAIL, getCurrentUserEmail())));

    userRepository.delete(entity);
  }

  @Override
  public String getCurrentUserEmail() {
    if (isUserNotAuthorized()) {
      return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
  }

  @Override
  public boolean isUserNotAuthorized() {
    return SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal().equals("anonymousUser");
  }
}
