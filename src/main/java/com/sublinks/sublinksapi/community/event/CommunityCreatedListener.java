package com.sublinks.sublinksapi.community.event;

import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommunityCreatedListener implements ApplicationListener<CommunityCreatedEvent> {
    private final InstanceAggregateRepository instanceAggregateRepository;

    public CommunityCreatedListener(final InstanceAggregateRepository instanceAggregateRepository) {
        this.instanceAggregateRepository = instanceAggregateRepository;
    }

    @Override
    public void onApplicationEvent(CommunityCreatedEvent event) {

        if (!event.getCommunity().isLocal()) {
            return;
        }
        InstanceAggregate instanceAggregate = event.getCommunity().getInstance().getInstanceAggregate();
        instanceAggregate.setCommunityCount(instanceAggregate.getCommunityCount() + 1);
        instanceAggregateRepository.save(instanceAggregate);
    }
}
