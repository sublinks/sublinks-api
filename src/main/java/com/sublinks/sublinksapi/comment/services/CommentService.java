package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
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
