package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.event.PersonCreatedPublisher;
import com.sublinks.sublinksapi.person.event.PersonUpdatedPublisher;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonService {
    private final KeyService keyService;
    private final PersonMapper personMapper;
    private final PersonRepository personRepository;
    private final PersonAggregateRepository personAggregateRepository;
    private final LocalInstanceContext localInstanceContext;
    private final PersonCreatedPublisher personCreatedPublisher;
    private final PersonUpdatedPublisher personUpdatedPublisher;

    public PersonContext getPersonContext(final Person person) {

        final Optional<PersonAggregate> personAggregates = Optional.ofNullable(personAggregateRepository.findFirstByPersonId(person.getId()));
        final List<Integer> discussLanguages = new ArrayList<>();
        discussLanguages.add(1);
        discussLanguages.add(38);
        //@todo person languages

        final Collection<Community> emptyCommunityList = new ArrayList<>();
        final Collection<Person> emptyPersonList = new ArrayList<>();
        final Collection<Post> emptyPostList = new ArrayList<>();
        final Collection<Comment> emptyCommentList = new ArrayList<>();

        return personMapper.PersonToPersonContext(
                person,
                emptyPostList,
                emptyCommentList,
                personAggregates.orElse(PersonAggregate.builder().person(person).build()),
                discussLanguages,
                emptyCommunityList,
                emptyCommunityList,
                emptyCommunityList,
                emptyPersonList
        );
    }

    @Transactional
    public void createPerson(final Person person) {

        // @todo if not local throw a fit
        final KeyStore keys = keyService.generate();
        person.setPublicKey(keys.publicKey());
        person.setPrivateKey(keys.privateKey());
        person.setLocal(true);
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

    public Person create(final String name) throws NoSuchAlgorithmException {

        // @todo actual create account stuff
        return Person.builder()
                .name(name)
                .password("")
                .instance(localInstanceContext.instance())
                .displayName("")
                .activityPubId("")
                .avatarImageUrl("")
                .bannerImageUrl("")
                .biography("")
                .isBanned(false)
                .build();
    }
}
