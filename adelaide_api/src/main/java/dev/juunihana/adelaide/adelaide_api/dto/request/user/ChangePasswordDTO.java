package dev.juunihana.adelaide.adelaide_api.dto.request.user;

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
public class ChangePasswordDTO {

  private String currentPassword;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 8, max = 24, message = "Password length must be between 8 and 24 characters")
  @Pattern(regexp = "[A-Za-z0-9_\\-.,!@#$%^&*(){}\\[\\]`~<>]+")
  private String newPassword;
}
