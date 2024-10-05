package dev.juunihana.adelaide.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitConsumer {

  private final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

  @RabbitListener(queues = "${rabbitmq-default-queue}")
  public void listen(String message) {
    logger.info(message);
  }
}
