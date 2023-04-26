package dev.juunihana.adelaide.adelaide_api.dto.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserNotFoundDTO {

  private String username;

  private String message = "User not found";
}
