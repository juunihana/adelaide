package dev.juunihana.adelaide.adelaide_cdn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images", uniqueConstraints = {
    @UniqueConstraint(name = "fileNameUniqueConstraint", columnNames = {"fileName"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String fileName;

  @Lob
  private byte[] fileContent;
}
