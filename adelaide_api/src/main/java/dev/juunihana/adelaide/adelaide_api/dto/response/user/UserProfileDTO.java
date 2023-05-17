package dev.juunihana.adelaide.adelaide_api.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class UserProfileDTO {

  private String firstName;

  private String middleName;

  private String lastName;

  private String maidenSurname;

  private Integer age;

  private String status;

  private String bio;

  private String phone;

  private String place;

  private String avatarLink;
}
