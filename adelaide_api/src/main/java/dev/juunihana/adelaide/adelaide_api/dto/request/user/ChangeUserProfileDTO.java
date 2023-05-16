package dev.juunihana.adelaide.adelaide_api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ChangeUserProfileDTO {

  @Pattern(regexp = "[A-Za-z]+", message =
      "First name cannot contain other characters than letters")
  private String firstName;

  @Pattern(regexp = "[A-Za-z]*", message =
      "Middle name cannot contain other characters than letters")
  private String middleName;

  @Pattern(regexp = "[A-Za-z]+", message =
      "Last name cannot contain other characters than letters")
  private String lastName;

  @Pattern(regexp = "[A-Za-z]*", message =
      "Maiden surname cannot contain other characters than letters")
  private String maidenSurname;

  private String bio;

  private String phone;

  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;
}
