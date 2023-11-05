package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.post.dto.PostAggregate;
import com.sublinks.sublinksapi.post.repositories.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostLikeUpdatedListener implements ApplicationListener<PostLikeUpdatedEvent> {
    private final PostAggregateRepository postAggregateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(PostLikeUpdatedEvent event) {
        final PostAggregate postAggregate = event.getPostLike().getPost().getPostAggregate();
        switch (event.getAction()) {
            case FROM_UP_TO_DOWN -> {
                postAggregate.setScore(postAggregate.getScore() - 2);
                postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() - 1);
                postAggregate.setDownVoteCount(postAggregate.getDownVoteCount() + 1);
            }
            case FROM_UP_TO_NEUTRAL -> {
                postAggregate.setScore(postAggregate.getScore() - 1);
                postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() - 1);
            }
            case FROM_DOWN_TO_UP -> {
                postAggregate.setScore(postAggregate.getScore() + 2);
                postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() + 1);
                postAggregate.setDownVoteCount(postAggregate.getDownVoteCount() - 1);
            }
            case FROM_DOWN_TO_NEUTRAL -> {
                postAggregate.setScore(postAggregate.getScore() + 1);
                postAggregate.setDownVoteCount(postAggregate.getDownVoteCount() - 1);
            }
            case FROM_NEUTRAL_TO_UP -> {
                postAggregate.setScore(postAggregate.getScore() + 1);
                postAggregate.setUpVoteCount(postAggregate.getUpVoteCount() + 1);
            }
            case FROM_NEUTRAL_TO_DOWN -> {
                postAggregate.setScore(postAggregate.getScore() - 1);
                postAggregate.setDownVoteCount(postAggregate.getDownVoteCount() + 1);
            }
            default -> {
                // do nothing
            }
        }
        postAggregateRepository.save(postAggregate);
    }
}
