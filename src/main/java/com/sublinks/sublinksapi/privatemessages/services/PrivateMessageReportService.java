package com.sublinks.sublinksapi.privatemessages.services;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessageReport;
import com.sublinks.sublinksapi.privatemessages.events.PrivateMessageReportCreatedPublisher;
import com.sublinks.sublinksapi.privatemessages.events.PrivateMessageReportUpdatedPublisher;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrivateMessageReportService {

  private final PrivateMessageReportRepository privateMessageReportRepository;
  private final PrivateMessageReportCreatedPublisher privateMessageReportCreatedPublisher;
  private final PrivateMessageReportUpdatedPublisher privateMessageReportUpdatedPublisher;

  @Transactional
  public void createPrivateMessageReport(final PrivateMessageReport privateMessageReport) {

    privateMessageReportRepository.save(privateMessageReport);
    privateMessageReportCreatedPublisher.publish(privateMessageReport);
  }

  @Transactional
  public void updatePrivateMessage(final PrivateMessageReport privateMessageReport) {

    privateMessageReportRepository.save(privateMessageReport);
    privateMessageReportUpdatedPublisher.publish(privateMessageReport);
  }

  public void resolveAllReportsByPerson(Person personToBan, Person resolver) {

    privateMessageReportRepository.resolveAllReportsByPerson(personToBan, resolver);
  }
}
