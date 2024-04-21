package com.sublinks.sublinksapi.privatemessages.services;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.comment.repositories.CommentReplyRepository;
import com.sublinks.sublinksapi.comment.services.CommentReplyService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.person.repositories.PersonMentionRepository;
import com.sublinks.sublinksapi.person.services.PersonMentionService;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.events.PrivateMessageCreatedPublisher;
import com.sublinks.sublinksapi.privatemessages.events.PrivateMessageDeletedPublisher;
import com.sublinks.sublinksapi.privatemessages.events.PrivateMessageUpdatedPublisher;
import com.sublinks.sublinksapi.privatemessages.models.MarkAllAsReadResponse;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateMessageService {

  private final PrivateMessageRepository privateMessageRepository;
  private final PrivateMessageCreatedPublisher privateMessageCreatedPublisher;
  private final PrivateMessageUpdatedPublisher privateMessageUpdatedPublisher;
  private final PrivateMessageDeletedPublisher privateMessageDeletedPublisher;
  private final LocalInstanceContext localInstanceContext;
  private final CommentReplyService commentReplyService;
  private final CommentReplyRepository commentReplyRepository;
  private final PersonMentionService personMentionService;
  private final PersonMentionRepository personMentionRepository;

  public String generateActivityPubId(
      final com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage privateMessage) {

    String domain = localInstanceContext.instance().getDomain();
    return String.format("%s/private_message/%d", domain, privateMessage.getId());
  }

  @Transactional
  public void createPrivateMessage(final PrivateMessage privateMessage) {

    privateMessage.setActivityPubId("");
    privateMessageRepository.save(privateMessage);
    privateMessage.setActivityPubId(this.generateActivityPubId(privateMessage));
    privateMessageRepository.save(privateMessage);

    privateMessageCreatedPublisher.publish(privateMessage);
  }

  @Transactional
  public void updatePrivateMessage(final PrivateMessage privateMessage) {

    privateMessageRepository.save(privateMessage);
    privateMessageUpdatedPublisher.publish(privateMessage);
  }

  @Transactional
  public void deletePrivateMessage(final PrivateMessage privateMessage) {

    privateMessageRepository.delete(privateMessage);
  }

  @Transactional
  public MarkAllAsReadResponse markAllAsRead(final Person person) {

    List<PrivateMessage> privateMessages = privateMessageRepository.findByRecipientAndReadIsFalse(
        person);
    privateMessages.forEach(privateMessage -> {
      privateMessage.setRead(true);
      this.updatePrivateMessage(privateMessage);
    });

    List<CommentReply> commentReplies = commentReplyRepository.findAllByRecipientAndReadIsFalse(
        person);

    commentReplies.forEach(commentReply -> {
      commentReply.setRead(true);
      commentReplyService.updateCommentReply(commentReply);
    });

    List<PersonMention> personMentions = personMentionRepository.findAllByRecipientAndReadIsFalse(
        person);

    personMentions.forEach(personMention -> {
      personMention.setRead(true);
      personMentionService.updatePersonMention(personMention);
    });

    return MarkAllAsReadResponse.builder()
        .privateMessages(privateMessages)
        .commentReplies(commentReplies)
        .personMentions(personMentions)
        .build();
  }

  @Transactional
  public List<PrivateMessage> deleteAllPrivateMessagesByPerson(final Person person) {

    final List<PrivateMessage> privateMessages = privateMessageRepository.findAllBySender(person);
    privateMessages.forEach(privateMessage -> {
      privateMessage.setDeleted(true);
      privateMessage.setRead(true);
      privateMessage.setContent("*Permanently deleted by creator*");

      privateMessageDeletedPublisher.publish(privateMessageRepository.save(privateMessage));
    });
    return privateMessages;
  }
}
