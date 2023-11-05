package com.sublinks.sublinksapi.comment.events;

import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentLikeUpdatedListener implements ApplicationListener<CommentLikeUpdatedEvent> {
    private final CommentAggregateRepository commentAggregateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(CommentLikeUpdatedEvent event) {
        final CommentAggregate commentAggregate = event.getCommentLike().getComment().getCommentAggregate();
        switch (event.getAction()) {
            case FROM_UP_TO_DOWN -> {
                commentAggregate.setScore(commentAggregate.getScore() - 2);
                commentAggregate.setUpVotes(commentAggregate.getUpVotes() - 1);
                commentAggregate.setDownVotes(commentAggregate.getDownVotes() + 1);
            }
            case FROM_UP_TO_NEUTRAL -> {
                commentAggregate.setScore(commentAggregate.getScore() - 1);
                commentAggregate.setUpVotes(commentAggregate.getUpVotes() - 1);
            }
            case FROM_DOWN_TO_UP -> {
                commentAggregate.setScore(commentAggregate.getScore() + 2);
                commentAggregate.setUpVotes(commentAggregate.getUpVotes() + 1);
                commentAggregate.setDownVotes(commentAggregate.getDownVotes() - 1);
            }
            case FROM_DOWN_TO_NEUTRAL -> {
                commentAggregate.setScore(commentAggregate.getScore() + 1);
                commentAggregate.setDownVotes(commentAggregate.getDownVotes() - 1);
            }
            case FROM_NEUTRAL_TO_UP -> {
                commentAggregate.setScore(commentAggregate.getScore() + 1);
                commentAggregate.setUpVotes(commentAggregate.getUpVotes() + 1);
            }
            case FROM_NEUTRAL_TO_DOWN -> {
                commentAggregate.setScore(commentAggregate.getScore() - 1);
                commentAggregate.setDownVotes(commentAggregate.getDownVotes() + 1);
            }
            default -> {
                // do nothing
                return;
            }
        }
        commentAggregateRepository.save(commentAggregate);
    }
}
