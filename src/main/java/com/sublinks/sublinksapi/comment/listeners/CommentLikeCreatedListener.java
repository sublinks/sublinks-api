package com.sublinks.sublinksapi.comment.listeners;

import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.events.CommentLikeCreatedEvent;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentLikeCreatedListener implements ApplicationListener<CommentLikeCreatedEvent> {
    private final CommentAggregateRepository commentAggregateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(CommentLikeCreatedEvent event) {

        final CommentAggregate commentAggregate = event.getCommentLike().getComment().getCommentAggregate();
        if (event.getCommentLike().isUpVote()) {
            commentAggregate.setUpVotes(commentAggregate.getUpVotes() + 1);
            commentAggregate.setScore(commentAggregate.getScore() + 1);
        } else if (event.getCommentLike().isDownVote()) {
            commentAggregate.setDownVotes(commentAggregate.getDownVotes() - 1);
            commentAggregate.setScore(commentAggregate.getScore() - 1);
        } else {
            return;
        }
        commentAggregateRepository.save(commentAggregate);
    }
}
