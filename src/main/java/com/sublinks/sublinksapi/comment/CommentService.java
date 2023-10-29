package com.sublinks.sublinksapi.comment;

import com.sublinks.sublinksapi.comment.event.CommentCreatedPublisher;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentCreatedPublisher commentCreatedPublisher;

    public CommentService(
            final CommentRepository commentRepository,
            final CommentCreatedPublisher commentCreatedPublisher) {
        this.commentRepository = commentRepository;
        this.commentCreatedPublisher = commentCreatedPublisher;
    }

    public void createComment(final Comment comment) {
        commentRepository.save(comment);
        commentCreatedPublisher.publish(comment);
    }
}
