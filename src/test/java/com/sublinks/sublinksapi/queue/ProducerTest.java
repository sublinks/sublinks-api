package com.sublinks.sublinksapi.queue;

import com.sublinks.sublinksapi.queue.services.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

public class ProducerTest {

  @Mock
  private RabbitTemplate rabbitTemplate;

  @InjectMocks
  private Producer producer;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void whenSendMessage_thenRabbitTemplateCalled() {
    String message = "mock-message";
    String exchange = "mock-exchange";
    String routingKey = "mock-routing-key";

    producer.sendMessage(exchange, routingKey, message);

    verify(rabbitTemplate).convertAndSend(exchange, routingKey, message);
  }
}
