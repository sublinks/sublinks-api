package com.sublinks.sublinksapi.queue.services;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  public void sendMessage(String exchange, String routingKey, Object msg) {

    this.rabbitTemplate.convertAndSend(exchange, routingKey, msg);
  }
}
