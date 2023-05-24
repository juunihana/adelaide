package dev.juunihana.adelaide.adelaide_cdn.service.impl;

import dev.juunihana.adelaide.adelaide_cdn.entity.ImageEntity;
import dev.juunihana.adelaide.adelaide_cdn.repository.ImageRepository;
import dev.juunihana.adelaide.adelaide_cdn.service.ImageService;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  @Override
  public byte[] getImage(String fileName) {
    ImageEntity image = imageRepository.findByFileName(fileName)
        .orElseThrow(RuntimeException::new);
    return image.getFileContent();
  }

  @Override
  public String save(MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();

    if (Objects.isNull(originalFileName)) {
      throw new RuntimeException();
    }

    UUID imageId = UUID.randomUUID();
    String fileName = imageId + "."
        + originalFileName.split("\\.")[originalFileName.split("\\.").length - 1];

    ImageEntity image = imageRepository.save(ImageEntity.builder()
        .id(imageId)
        .fileName(fileName)
        .fileContent(file.getBytes())
        .build());

    return image.getFileName();
  }
}
