package com.sublinks.sublinksapi.queue.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(RabbitTemplate.class)
public class Consumer {

  /*@RabbitListener(queues = "${sublinks.federation_queue.name}")
  public void receive(Object msg) {
    // @todo add message processing
  }*/
}
