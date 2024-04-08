package com.sublinks.sublinksapi.queue.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(RabbitTemplate.class)
public class Producer {
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public Producer(RabbitTemplate rabbitTemplate) {
    System.out.println("registering the producer!!!!!!!!!!!!");
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String exchange, String routingKey, Object msg) {
    this.rabbitTemplate.convertAndSend(exchange, routingKey, msg);
  }
}
