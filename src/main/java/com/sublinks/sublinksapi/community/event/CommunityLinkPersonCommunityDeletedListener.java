package com.sublinks.sublinksapi.community.event;

import com.sublinks.sublinksapi.community.CommunityAggregate;
import com.sublinks.sublinksapi.community.CommunityAggregateRepository;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.event.LinkPersonCommunityDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommunityLinkPersonCommunityDeletedListener implements ApplicationListener<LinkPersonCommunityDeletedEvent> {
    private final CommunityAggregateRepository communityAggregateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(LinkPersonCommunityDeletedEvent event) {

        if (event.getLinkPersonCommunity().getLinkType() == LinkPersonCommunityType.follower) {
            final CommunityAggregate communityAggregate = event.getLinkPersonCommunity().getCommunity().getCommunityAggregate();
            communityAggregate.setSubscriberCount(communityAggregate.getSubscriberCount() - 1);
            communityAggregateRepository.save(communityAggregate);
        }
    }
}
