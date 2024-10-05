package dev.juunihana.adelaide.dto.user;

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
public class UpdateUserDto {

  private String id;

  private String email;

  private String phone;

  private String password;

  private String firstName;

  private String lastName;

  private Integer age;
}
