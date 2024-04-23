package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.services;

import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPrivateMessageService {

  private final ConversionService conversionService;

  public PrivateMessageView createPrivateMessageView(
      final com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage privateMessage
  ) {

    return privateMessageViewBuilder(privateMessage).build();
  }

  private PrivateMessageView.PrivateMessageViewBuilder privateMessageViewBuilder(
      final com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage privateMessage
  ) {

    final PrivateMessage lemmyPrivateMessage = conversionService.convert(privateMessage,
        PrivateMessage.class);

    final com.sublinks.sublinksapi.person.entities.Person sender = privateMessage.getSender();
    final Person lemmySender = conversionService.convert(sender, Person.class);

    final com.sublinks.sublinksapi.person.entities.Person recipient = privateMessage.getRecipient();
    final Person lemmyRecipient = conversionService.convert(recipient, Person.class);

    return PrivateMessageView.builder()
        .private_message(lemmyPrivateMessage).creator(lemmySender).recipient(lemmyRecipient);
  }

}
