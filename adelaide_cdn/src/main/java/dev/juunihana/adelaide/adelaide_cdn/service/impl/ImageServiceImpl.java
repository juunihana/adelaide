package dev.juunihana.adelaide.adelaide_cdn.service.impl;

import dev.juunihana.adelaide.adelaide_cdn.entity.ImageEntity;
import dev.juunihana.adelaide.adelaide_cdn.repository.ImageRepository;
import dev.juunihana.adelaide.adelaide_cdn.service.ImageService;
import java.io.IOException;
import java.util.Map;
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
  public Map<String, String> save(MultipartFile file) throws IOException {
    ImageEntity image = imageRepository.save(ImageEntity.builder()
        .fileName(file.getName())
        .fileContent(file.getBytes())
        .build());

    return Map.of("id", image.getId().toString(), "name", image.getFileName());
  }
}
