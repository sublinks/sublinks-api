package com.sublinks.sublinksapi.comment;

import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentCreatedPublisher commentCreatedPublisher;

    public void createComment(final Comment comment) {
        commentRepository.save(comment);
        commentCreatedPublisher.publish(comment);
    }
}
