package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.VoteApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.vote.CreateVoteDTO;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController implements VoteApi {

  private final PostService postService;
  private final VoteService voteService;

  @Override
  public void create(CreateVoteDTO dto) {
    switch (dto.getTargetType()) {
      case "post" -> postService.addVote(dto);
    }
  }

  @Override
  public void update(String voteId, boolean upVote) {
    voteService.update(voteId, upVote);
  }

  @Override
  public void delete(String voteId) {
    voteService.delete(voteId);
  }
}
