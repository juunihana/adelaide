package dev.juunihana.adelaide.adelaide_api.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cdn")
@Data
@RequiredArgsConstructor
public class CdnProperties {

  private String url;
}
