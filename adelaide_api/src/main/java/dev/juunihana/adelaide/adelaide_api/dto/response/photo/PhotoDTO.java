package dev.juunihana.adelaide.adelaide_api.dto.response.photo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.juunihana.adelaide.adelaide_api.dto.response.comment.CommentDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import java.util.List;
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
public class PhotoDTO {

  private UUID id;

  private String url;

  private UserCompactDTO user;

  private List<CommentDTO> comments;

  private int upVotes;

  private int downVotes;
}
