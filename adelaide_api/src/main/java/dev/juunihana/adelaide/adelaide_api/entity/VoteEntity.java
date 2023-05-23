package dev.juunihana.adelaide.adelaide_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  private boolean upvote;

  private LocalDateTime timeVoted;

  @ManyToOne
  @JoinTable(name = "posts_votes",
      joinColumns = @JoinColumn(name = "vote_id"),
      inverseJoinColumns = @JoinColumn(name = "post_id"))
  private PostEntity post;
}
