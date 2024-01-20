package com.sublinks.sublinksapi.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
  @Value("${FEDERATION_QUEUE_NAME}")
  private String federationQueueName;

  @Value("${FEDERATION_TOPIC_NAME}")
  private String federationTopicName;

  @Bean
  public Queue federationQueue() {
    return new Queue(this.federationQueueName, true);
  }

  @Bean
  public TopicExchange federationTopicExchange() {
    return new TopicExchange(this.federationTopicName);
  }

  @Bean
  public Binding binding(Queue federationQueue, TopicExchange federationTopicExchange) {
    return BindingBuilder.bind(federationQueue).to(federationTopicExchange).with("");
  }
}
