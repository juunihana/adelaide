package dev.juunihana.adelaide.adelaide_api.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.ShortUserProfileDTO;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class PostDTO {

  private UUID id;

  private ShortUserProfileDTO author;

  private String title;

  private String content;

  private String timeCreated;

  private String timeEdited;
}
