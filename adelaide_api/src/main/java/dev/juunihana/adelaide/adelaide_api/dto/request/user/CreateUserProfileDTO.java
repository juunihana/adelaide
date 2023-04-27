package dev.juunihana.adelaide.adelaide_api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class CreateUserProfileDTO {

  @NotBlank(message = "Email cannot be empty")
  @Pattern(regexp = "^[A-Za-z0-9_\\-.]+@[A-Za-z0-9.\\-_]+.[A-Za-z]{2,}$",
      message = "Email is incorrect")
  private String email;

  @NotBlank(message = "Username cannot be empty")
  @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 8, max = 24, message = "Password length must be between 8 and 24 characters")
  @Pattern(regexp = "[A-Za-z0-9_\\-.,!@#$%^&*(){}\\[\\]`~<>]+")
  private String password;

  @NotBlank(message = "First name cannot be empty")
  @Pattern(regexp = "[A-Za-z]+", message = "First name cannot contain other characters than letters")
  private String firstName;

  @Pattern(regexp = "[A-Za-z]*", message = "Middle name cannot contain other characters than letters")
  private String middleName;

  @NotBlank(message = "Last name cannot be empty")
  @Pattern(regexp = "[A-Za-z]+", message = "Last name cannot contain other characters than letters")
  private String lastName;

  @Pattern(regexp = "[A-Za-z]*", message = "Maiden name cannot contain other characters than letters")
  private String maidenSurname;

  private String info;

  private String phone;

  private String place;

  @NotNull(message = "Date of birth cannot be empty")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;

  @NotNull(message = "Agreement cannot be empty")
  @AssertTrue(message = "You must accept EULA terms")
  private Boolean agreement;
}
