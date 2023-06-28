package dev.juunihana.adelaide.adelaide_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

  @Id
  private UUID id;

  private String username;

  private String email;

  @Column(name = "password_hash")
  private String password;

  private String displayName;

  private String avatar;

  private String bio;

  private LocalDate dateOfBirth;

  private String place;

  private LocalDateTime timeJoined;

  private Boolean deleted;

  @ManyToMany
  @JoinTable(name = "users_friends",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "friend_id"))
  private List<UserEntity> friends;

  @ManyToMany
  @JoinTable(name = "users_friends_requests",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "friend_id"))
  private List<UserEntity> incomingFriendsRequests;

  @ManyToMany
  @JoinTable(name = "users_friends_requests",
      joinColumns = @JoinColumn(name = "friend_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<UserEntity> outgoingFriendsRequests;

  @ManyToMany
  @JoinTable(name = "users_communities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "community_id"))
  private List<CommunityEntity> communities;

  @OneToMany
  private List<PostEntity> posts;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER_NOT_ANON"));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
