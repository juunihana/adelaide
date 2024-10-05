package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UpdateUserDto;
import dev.juunihana.adelaide.dto.user.UserAuthTokenDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import dev.juunihana.adelaide.dto.user.UserSignInDto;
import dev.juunihana.adelaide.service.JwtService;
import dev.juunihana.adelaide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  /**
   * User signing-in process
   *
   * @param signIn User credentials DTO
   * @return JWT for authorization
   */
  @PostMapping("/sign-in")
  public UserAuthTokenDto signIn(
      @RequestBody UserSignInDto signIn
  ) {
    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return UserAuthTokenDto.builder()
        .token(jwtService.createToken(signIn.getEmail()))
        .build();
  }

  @GetMapping
  public UserFullDto getFull() {
    return userService.getFull();
  }

  /**
   * User registration process
   *
   * @param createUserDto User creation DTO
   * @return JWT for authorization
   */
  @PostMapping
  public UserAuthTokenDto create(
      @RequestBody CreateUserDto createUserDto
  ) {
    userService.create(createUserDto);

    return UserAuthTokenDto.builder()
        .token(jwtService.createToken(createUserDto.getEmail()))
        .build();
  }

  /**
   * Change user's data
   *
   * @param updateUserDto User update DTO
   * @return JWT for authorization
   */
  @PutMapping
  public UserAuthTokenDto update(
      @RequestBody UpdateUserDto updateUserDto
  ) {
    userService.update(updateUserDto);

    return UserAuthTokenDto.builder()
        .token(jwtService.createToken(updateUserDto.getEmail()))
        .build();
  }

  /**
   * Delete current user
   */
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete() {
    userService.delete();
  }
}
