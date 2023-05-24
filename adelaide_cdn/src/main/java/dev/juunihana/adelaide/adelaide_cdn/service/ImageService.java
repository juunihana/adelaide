package dev.juunihana.adelaide.adelaide_cdn.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  byte[] getImage(String fileName);

  String save(MultipartFile file) throws IOException;
}
