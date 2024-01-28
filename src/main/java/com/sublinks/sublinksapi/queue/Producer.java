package com.sublinks.sublinksapi.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String exchange, String routingKey, Object msg) {
    this.rabbitTemplate.convertAndSend(exchange, routingKey, msg);
  }
}
