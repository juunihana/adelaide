package dev.juunihana.adelaide.adelaide_api.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddVoteDTO {

  private String postId;

  private boolean upVote;
}
