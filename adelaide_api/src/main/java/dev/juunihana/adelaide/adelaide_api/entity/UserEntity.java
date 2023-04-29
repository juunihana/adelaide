package dev.juunihana.adelaide.adelaide_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String username;

  private String email;

  @Column(name = "password_hash")
  private String password;

  private String phone;

  private String firstName;

  private String middleName;

  private String lastName;

  private String maidenSurname;

  private String bio;

  private String status;

  private LocalDate dateOfBirth;

  private String place;

  private LocalDateTime timeJoined;
}
