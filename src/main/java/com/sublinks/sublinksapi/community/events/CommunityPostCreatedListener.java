package com.sublinks.sublinksapi.community.events;

import com.sublinks.sublinksapi.community.CommunityAggregate;
import com.sublinks.sublinksapi.community.CommunityAggregateRepository;
import com.sublinks.sublinksapi.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommunityPostCreatedListener implements ApplicationListener<PostCreatedEvent> {
    private final CommunityAggregateRepository communityAggregateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(PostCreatedEvent event) {
        final CommunityAggregate communityAggregate = event.getPost().getCommunity().getCommunityAggregate();
        communityAggregate.setPostCount(communityAggregate.getPostCount() + 1);
        communityAggregateRepository.save(communityAggregate);
    }
}
