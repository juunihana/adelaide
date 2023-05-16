package dev.juunihana.adelaide.adelaide_api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ChangeEmailDTO {

  @NotBlank(message = "Email cannot be empty")
  @Pattern(regexp = "^[A-Za-z0-9_\\-.]+@[A-Za-z0-9.\\-_]+.[A-Za-z]{2,}$",
      message = "Email is incorrect")
  private String newEmail;

  @NotBlank(message = "Password cannot be empty")
  private String password;
}
