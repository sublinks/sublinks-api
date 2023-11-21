package com.sublinks.sublinksapi.instance.listeners;

import com.sublinks.sublinksapi.instance.dto.InstanceAggregate;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.instance.repositories.InstanceAggregateRepository;
import com.sublinks.sublinksapi.person.events.PersonCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InstancePersonCreatedListener implements ApplicationListener<PersonCreatedEvent> {

  private final InstanceAggregateRepository instanceAggregateRepository;
  private final LocalInstanceContext localInstanceContext;

  public InstancePersonCreatedListener(
      final InstanceAggregateRepository instanceAggregateRepository,
      LocalInstanceContext localInstanceContext
  ) {

    this.instanceAggregateRepository = instanceAggregateRepository;
    this.localInstanceContext = localInstanceContext;
  }

  @Override
  public void onApplicationEvent(final PersonCreatedEvent event) {

    if (!event.getPerson().isLocal()) {
      return;
    }

    final InstanceAggregate instanceAggregate = localInstanceContext.instanceAggregate();
    instanceAggregate.setUserCount(instanceAggregate.getUserCount() + 1);
    instanceAggregateRepository.save(instanceAggregate);
  }
}
