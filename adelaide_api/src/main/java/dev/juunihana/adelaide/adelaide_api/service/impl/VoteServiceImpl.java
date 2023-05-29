package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.VoteNotFoundException;
import dev.juunihana.adelaide.adelaide_api.repository.VoteRepository;
import dev.juunihana.adelaide.adelaide_api.service.VoteService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final VoteRepository voteRepository;

  @Override
  public void update(String voteId, boolean upVote) {
    VoteEntity vote = findByIdInternal(voteId);

    if (!vote.getUser().getUsername().equals(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername())) {
      throw new AccessDeniedException("You can't edit this user's votes");
    }

    vote.setUpVote(upVote);
    vote.setTimeVoted(LocalDateTime.now());
    voteRepository.save(vote);
  }

  @Override
  public void delete(String voteId) {
    voteRepository.delete(findByIdInternal(voteId));
  }

  private VoteEntity findByIdInternal(String voteId) {
    return voteRepository.findById(UUID.fromString(voteId))
        .orElseThrow(() -> new VoteNotFoundException(voteId));
  }
}
