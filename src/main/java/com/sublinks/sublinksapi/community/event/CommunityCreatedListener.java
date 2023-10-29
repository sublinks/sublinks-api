package com.sublinks.sublinksapi.community.event;

import com.sublinks.sublinksapi.community.CommunityAggregateRepository;
import com.sublinks.sublinksapi.instance.InstanceAggregate;
import com.sublinks.sublinksapi.instance.InstanceAggregateRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommunityCreatedListener implements ApplicationListener<CommunityCreatedEvent> {
    private final InstanceAggregateRepository instanceAggregateRepository;

    public CommunityCreatedListener(final InstanceAggregateRepository instanceAggregateRepository, CommunityAggregateRepository communityAggregateRepository) {
        this.instanceAggregateRepository = instanceAggregateRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CommunityCreatedEvent event) {

        if (!event.getCommunity().isLocal()) {
            return;
        }
        final InstanceAggregate instanceAggregate = event.getCommunity().getInstance().getInstanceAggregate();
        instanceAggregate.setCommunityCount(instanceAggregate.getCommunityCount() + 1);
        instanceAggregateRepository.save(instanceAggregate);
    }
}
