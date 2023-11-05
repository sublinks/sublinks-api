package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentAggregateRepository commentAggregateRepository;
    private final CommentCreatedPublisher commentCreatedPublisher;

    @Transactional
    public void createComment(final Comment comment) {

        CommentAggregate commentAggregate = CommentAggregate.builder()
                .comment(comment)
                .build();
        commentRepository.save(comment);
        commentAggregateRepository.save(commentAggregate);
        comment.setCommentAggregate(commentAggregate);
        commentCreatedPublisher.publish(comment);
    }
}
