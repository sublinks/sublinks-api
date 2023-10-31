package com.sublinks.sublinksapi.instance.events;

import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.post.event.PostCreatedEvent;
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
