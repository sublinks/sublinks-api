package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.services;

import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessageView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPrivateMessageService {

  private final ConversionService conversionService;

  public PrivateMessageView createPrivateMessageView(
      final com.sublinks.sublinksapi.private_messages.dto.PrivateMessage privateMessage
  ) {

    return privateMessageViewBuilder(privateMessage).build();
  }

  private PrivateMessageView.PrivateMessageViewBuilder privateMessageViewBuilder(
      final com.sublinks.sublinksapi.private_messages.dto.PrivateMessage privateMessage
  ) {

    final PrivateMessage lemmyPrivateComment = conversionService.convert(privateMessage,
        PrivateMessage.class);

    final com.sublinks.sublinksapi.person.dto.Person sender = privateMessage.getSender();
    final Person lemmySender = conversionService.convert(sender, Person.class);

    final com.sublinks.sublinksapi.person.dto.Person recipient = privateMessage.getRecipient();
    final Person lemmyRecipient = conversionService.convert(recipient, Person.class);

    return PrivateMessageView.builder()
        .private_message(lemmyPrivateComment).creator(lemmySender).recipient(lemmyRecipient);
  }

}
