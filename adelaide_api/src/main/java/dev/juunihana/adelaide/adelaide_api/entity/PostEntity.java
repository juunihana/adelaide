package dev.juunihana.adelaide.adelaide_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private UserEntity author;

  @ManyToOne
  @JoinColumn(name = "community_id")
  private CommunityEntity community;

  @OneToOne
  @JoinColumn(name = "reply_to_id")
  private PostEntity parent;

  private String title;

  private String content;

  private LocalDateTime timeCreated;

  private LocalDateTime timeEdited;

  private Boolean deleted;

  @ManyToMany
  @JoinTable(name = "posts_tags",
      joinColumns = @JoinColumn(name = "post_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagEntity> tags;

  @OneToMany
  @JoinColumn(name = "reply_to_id")
  private List<PostEntity> comments;

  @OneToMany
  private Set<VoteEntity> votes;
}
