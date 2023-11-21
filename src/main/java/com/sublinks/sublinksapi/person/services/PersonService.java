package com.sublinks.sublinksapi.person.services;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.dto.LinkPersonInstance;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonAggregate;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import com.sublinks.sublinksapi.person.events.PersonCreatedPublisher;
import com.sublinks.sublinksapi.person.events.PersonUpdatedPublisher;
import com.sublinks.sublinksapi.person.repositories.PersonAggregateRepository;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.utils.KeyGeneratorUtil;
import com.sublinks.sublinksapi.utils.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PersonService {

  private final KeyGeneratorUtil keyGeneratorUtil;
  private final PersonRepository personRepository;
  private final PersonAggregateRepository personAggregateRepository;
  private final LocalInstanceContext localInstanceContext;
  private final PersonCreatedPublisher personCreatedPublisher;
  private final PersonUpdatedPublisher personUpdatedPublisher;

  @Transactional
  public Optional<Language> getPersonDefaultPostLanguage(final Person person,
      final Community community) {

    for (Language language : person.getLanguages()) {
      if (community.getLanguages().contains(language)) {
        return Optional.of(language);
      }
    }
    return Optional.empty();
  }

  @Transactional
  public void createPerson(final Person person) {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    person.setPassword(passwordEncoder.encode(person.getPassword()));

    final KeyStore keys = keyGeneratorUtil.generate();
    person.setPublicKey(keys.publicKey());
    person.setPrivateKey(keys.privateKey());
    person.setLocal(true);

    LinkPersonInstanceType accountLevel = localInstanceContext.instance().getDomain().isEmpty()
        ? LinkPersonInstanceType.super_admin : LinkPersonInstanceType.user;

    person.setLinkPersonInstance(LinkPersonInstance.builder()
        .instance(localInstanceContext.instance())
        .linkType(accountLevel)
        .person(person)
        .build());

    final List<Language> languages = new ArrayList<>(
        localInstanceContext.instance().getLanguages());
    person.setLanguages(languages);
    personRepository.save(person);

    final PersonAggregate personAggregate = PersonAggregate.builder().person(person).build();
    personAggregateRepository.save(personAggregate);
    person.setPersonAggregate(personAggregate);

    personCreatedPublisher.publish(person);
  }

  @Transactional
  public void updatePerson(final Person person) {

    personRepository.save(person);
    personUpdatedPublisher.publish(person);
  }

  public Person getDefaultNewUser(final String name) {

    return Person.builder()
        .name(name)
        //.instance(localInstanceContext.instance())
        .password("")
        .displayName("")
        .activityPubId("")
        .avatarImageUrl("")
        .bannerImageUrl("")
        .biography("")
        .isBanned(false)
        .isShowAvatars(true)
        .isShowScores(true)
        .isShowAvatars(true)
        .isShowReadPosts(true)
        .isShowNsfw(true)
        .build();
  }
}
