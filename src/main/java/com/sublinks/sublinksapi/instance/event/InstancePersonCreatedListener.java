package com.sublinks.sublinksapi.instance.event;

import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.event.PersonCreatedEvent;
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
