package dev.juunihana.adelaide.adelaide_cdn.controller;

import dev.juunihana.adelaide.adelaide_cdn.service.ImageService;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/{fileName}")
  public byte[] getImage(
      @PathVariable String fileName) {
    return imageService.getImage(fileName);
  }

  @PostMapping("/new")
  public Map<String, String> saveImage(
      @RequestParam("image") MultipartFile image
  ) throws IOException {
    return imageService.save(image);
  }
}
