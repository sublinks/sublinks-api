package com.sublinks.sublinksapi.federation.listeners;

import com.sublinks.sublinksapi.community.events.CommunityCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RabbitTemplate.class)
public class CommunityActorCreateListener implements ApplicationListener<CommunityCreatedEvent> {

  @Override
  public void onApplicationEvent(CommunityCreatedEvent event) {

  }
}
