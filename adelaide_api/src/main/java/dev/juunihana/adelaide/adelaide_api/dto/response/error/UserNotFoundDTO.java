package dev.juunihana.adelaide.adelaide_api.dto.response.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserNotFoundDTO {

  private String message;

  private String username;
}
