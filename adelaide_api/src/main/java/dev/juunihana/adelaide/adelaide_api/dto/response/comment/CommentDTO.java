package dev.juunihana.adelaide.adelaide_api.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
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
public class CommentDTO {

  private UUID id;

  private UserCompactDTO author;

  private String content;

  private int upVotes;

  private int downVotes;

  private String timeCreated;
}
