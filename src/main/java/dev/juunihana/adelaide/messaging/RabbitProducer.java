package dev.juunihana.adelaide.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitProducer {

  @Value("${rabbitmq-default-queue}")
  private String defaultQueue;

  private final RabbitTemplate rabbitTemplate;

  public void send(String message) {
    rabbitTemplate.convertAndSend(defaultQueue, message);
  }
}
