package dev.juunihana.adelaide.adelaide_api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class PreCheckSignUpDTO {

  @NotBlank(message = "Email cannot be empty")
  @Pattern(regexp = "^[A-Za-z0-9_\\-.]+@[A-Za-z0-9.\\-_]+.[A-Za-z]{2,}$",
      message = "Email is incorrect")
  private String email;

  @NotBlank(message = "Username cannot be empty")
  @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters")
  @Pattern(regexp = "[A-Za-z0-9]+", message = "Username can only contain letters and numbers")
  @Pattern(regexp = "^[A-Za-z]+[A-Za-z0-9]+", message = "Username cannot begin with number")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 8, max = 24, message = "Password length must be between 8 and 24 characters")
  @Pattern(regexp = "[A-Za-z0-9_\\-.,!@#$%^&*(){}\\[\\]`~<>]+")
  private String password;
}
