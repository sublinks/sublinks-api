package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.instance.LocalInstanceContext;
import com.sublinks.sublinksapi.person.event.PersonCreatedPublisher;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.util.KeyService;
import com.sublinks.sublinksapi.util.KeyStore;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class PersonService {
    private final KeyService keyService;
    private final PersonMapper personMapper;
    private final PersonRepository personRepository;
    private final PersonAggregatesRepository personAggregatesRepository;
    private final LocalInstanceContext localInstanceContext;
    private final PersonCreatedPublisher personCreatedPublisher;

    public PersonService(
            final KeyService keyService,
            final PersonMapper personMapper,
            final PersonRepository personRepository,
            final PersonAggregatesRepository personAggregatesRepository,
            final LocalInstanceContext localInstanceContext,
            final PersonCreatedPublisher personCreatedPublisher
    ) {
        this.keyService = keyService;
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.personAggregatesRepository = personAggregatesRepository;
        this.localInstanceContext = localInstanceContext;
        this.personCreatedPublisher = personCreatedPublisher;
    }

    public PersonContext getPersonContext(final Person person) {

        final Optional<PersonAggregates> personAggregates = Optional.ofNullable(personAggregatesRepository.findFirstByPersonId(person.getId()));
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
                personAggregates.orElse(PersonAggregates.builder().person(person).build()),
                discussLanguages,
                emptyCommunityList,
                emptyCommunityList,
                emptyCommunityList,
                emptyPersonList
        );
    }

    public void createLocalPerson(final Person person) {

        final KeyStore keys = keyService.generate();
        person.setPublicKey(keys.publicKey());
        person.setPrivateKey(keys.privateKey());
        person.setLocal(true);
        personRepository.save(person);

        final PersonAggregates personAggregates = PersonAggregates.builder().person(person).build();
        personAggregatesRepository.save(personAggregates);
        person.setPersonAggregates(personAggregates);
        personCreatedPublisher.publish(person);
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
