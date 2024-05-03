package dev.juunihana.adelaide.config.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

  @Value("${rabbitmq-default-queue}")
  private String defaultQueue;

  @Bean
  public Queue defaultQueue() {
    return new Queue(defaultQueue, true, false, false);
  }
}
