package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.person.events.PersonMentionCreatedPublisher;
import com.sublinks.sublinksapi.person.events.PersonMentionUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.PersonMentionRepository;
import com.sublinks.sublinksapi.utils.KeyGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PersonMentionService {
    private final KeyGeneratorUtil keyGeneratorUtil;
    private final PersonMentionRepository personMentionsRepository;
    private final PersonMentionCreatedPublisher personMentionCreatedPublisher;
    private final PersonMentionUpdatedPublisher personUpdatedPublisher;

    @Transactional
    public void createPersonMention(final PersonMention personMention) {
        personMentionsRepository.save(personMention);
        personMentionCreatedPublisher.publish(personMention);
    }

    @Transactional
    public void updatePersonMention(final PersonMention personMention) {
        personMentionsRepository.save(personMention);
        personUpdatedPublisher.publish(personMention);
    }
}
