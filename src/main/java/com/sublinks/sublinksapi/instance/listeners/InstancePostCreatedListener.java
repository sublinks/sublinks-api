package com.sublinks.sublinksapi.instance.listeners;

import com.sublinks.sublinksapi.instance.dto.InstanceAggregate;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceAggregateRepository;
import com.sublinks.sublinksapi.post.events.PostCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstancePostCreatedListener implements ApplicationListener<PostCreatedEvent> {

  private final InstanceAggregateRepository instanceAggregateRepository;
  private final LocalInstanceContext localInstanceContext;

  public InstancePostCreatedListener(
      final InstanceAggregateRepository instanceAggregateRepository,
      LocalInstanceContext localInstanceContext
  ) {

    this.instanceAggregateRepository = instanceAggregateRepository;
    this.localInstanceContext = localInstanceContext;
  }

  @Override
  public void onApplicationEvent(final PostCreatedEvent event) {

    if (!event.getPost().isLocal()) {
      return;
    }

    final InstanceAggregate instanceAggregate = localInstanceContext.instanceAggregate();
    instanceAggregate.setPostCount(instanceAggregate.getPostCount() + 1);
    instanceAggregateRepository.save(instanceAggregate);
  }
}
