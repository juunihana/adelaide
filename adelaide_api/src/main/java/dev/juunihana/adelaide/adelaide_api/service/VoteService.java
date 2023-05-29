package dev.juunihana.adelaide.adelaide_api.service;

public interface VoteService {

  void update(String voteId, boolean upVote);

  void delete(String voteId);
}
