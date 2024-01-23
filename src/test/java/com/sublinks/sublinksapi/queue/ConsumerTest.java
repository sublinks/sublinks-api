package com.sublinks.sublinksapi.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

public class ConsumerTest {
  @InjectMocks
  private Consumer consumer;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void whenReceiveMessage_processMessage() {
    String msg = "mock-msg";

    consumer.receive(msg);

    // @todo: add tests for messaging processing
  }
}
