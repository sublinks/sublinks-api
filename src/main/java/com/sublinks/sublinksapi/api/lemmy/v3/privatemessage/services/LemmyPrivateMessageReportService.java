package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.services;

import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessage;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageReport;
import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageReportView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmyPrivateMessageReportService {

  private final ConversionService conversionService;

  public PrivateMessageReportView createPrivateMessageReportView(
      final com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport privateMessageReport
  ) {

    return privateMessageReportViewBuilder(privateMessageReport).build();
  }

  private PrivateMessageReportView.PrivateMessageReportViewBuilder privateMessageReportViewBuilder(
      final com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport privateMessageReport
  ) {

    final PrivateMessageReport lemmyPrivateMessageReport = conversionService.convert(
        privateMessageReport,
        PrivateMessageReport.class);

    final PrivateMessage lemmyPrivateMessage = conversionService.convert(
        privateMessageReport.getPrivateMessage(),
        PrivateMessage.class);

    final com.sublinks.sublinksapi.person.entities.Person sender = privateMessageReport.getPrivateMessage()
        .getSender();
    final Person lemmySender = conversionService.convert(sender, Person.class);

    final com.sublinks.sublinksapi.person.entities.Person creator = privateMessageReport.getCreator();
    final Person lemmyCreator = conversionService.convert(creator, Person.class);

    final com.sublinks.sublinksapi.person.entities.Person resolver = privateMessageReport.getResolver();
    final Person lemmyResolver = conversionService.convert(resolver, Person.class);

    return PrivateMessageReportView.builder()
        .private_message(lemmyPrivateMessage)
        .creator(lemmyCreator)
        .private_message_report(lemmyPrivateMessageReport)
        .private_message_creator(lemmySender)
        .resolver(lemmyResolver);
  }

}
