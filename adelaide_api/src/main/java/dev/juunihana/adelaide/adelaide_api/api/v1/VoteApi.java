package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.vote.CreateVoteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/votes")
public interface VoteApi {

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  void create(@RequestBody CreateVoteDTO dto);

  @PutMapping("/{voteId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void update(@PathVariable String voteId, @RequestParam boolean upVote);

  @DeleteMapping("/{voteId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void delete(@PathVariable String voteId);
}
