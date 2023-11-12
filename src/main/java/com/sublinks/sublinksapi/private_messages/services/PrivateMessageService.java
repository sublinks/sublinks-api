package com.sublinks.sublinksapi.private_messages.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentAggregate;
import com.sublinks.sublinksapi.comment.events.CommentCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentUpdatedPublisher;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.events.PrivateMessageCreatedPublisher;
import com.sublinks.sublinksapi.private_messages.events.PrivateMessageUpdatedPublisher;
import com.sublinks.sublinksapi.private_messages.repositories.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrivateMessageService {
    private final PrivateMessageRepository privateMessageRepository;
    private final PrivateMessageCreatedPublisher privateMessageCreatedPublisher;
    private final PrivateMessageUpdatedPublisher privateMessageUpdatedPublisher;

    @Transactional
    public void createPrivateMessage(final PrivateMessage privateMessage) {
        privateMessageRepository.save(privateMessage);
        privateMessageCreatedPublisher.publish(privateMessage);
    }

    @Transactional
    public void updatePrivateMessage(final PrivateMessage comment) {
            privateMessageRepository.save(comment);
            privateMessageUpdatedPublisher.publish(comment);
    }
}
