package com.sublinks.sublinksapi.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
  @Value("${BACKEND_QUEUE_NAME}")
  private String backendQueueName;

  @Value("${BACKEND_TOPIC_NAME}")
  private String backendTopicName;

  // set default to an empty string
  @Value("${FEDERATION_ROUTING_KEY:}")
  private String federationRoutingKey;

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jsonMessageConverter());
    return factory;
  }

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue federationQueue() {
    return new Queue(this.backendQueueName, true);
  }

  @Bean
  public TopicExchange federationTopicExchange() {
    return new TopicExchange(this.backendTopicName);
  }

  @Bean
  public Binding binding(Queue federationQueue, TopicExchange federationTopicExchange) {
    return BindingBuilder.bind(federationQueue).to(federationTopicExchange).with(federationRoutingKey);
  }
}
