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
public class ChangeUsernameDTO {

  @NotBlank(message = "Username cannot be empty")
  @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters")
  @Pattern(regexp = "[A-Za-z0-9]+", message = "Username can only contain letters and numbers")
  @Pattern(regexp = "^[A-Za-z]+[A-Za-z0-9]+", message = "Username cannot begin with number")
  private String newUsername;

  @NotBlank(message = "Password cannot be empty")
  private String password;
}
