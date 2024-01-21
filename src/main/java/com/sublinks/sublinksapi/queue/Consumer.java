package com.sublinks.sublinksapi.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

  @RabbitListener(queues = "${FEDERATION_QUEUE_NAME}")
  public void receive(Object msg) {
    // TODO: add message processing
  }
}
