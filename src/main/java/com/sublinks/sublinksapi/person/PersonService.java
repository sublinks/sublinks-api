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
    final private PersonCreatedPublisher personCreatedPublisher;

    public PersonService(
            KeyService keyService,
            PersonMapper personMapper,
            PersonRepository personRepository,
            PersonAggregatesRepository personAggregatesRepository,
            LocalInstanceContext localInstanceContext,
            PersonCreatedPublisher personCreatedPublisher
    ) {
        this.keyService = keyService;
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.personAggregatesRepository = personAggregatesRepository;
        this.localInstanceContext = localInstanceContext;
        this.personCreatedPublisher = personCreatedPublisher;
    }

    public PersonContext getPersonContext(Person person) {
        Optional<PersonAggregates> personAggregates = Optional.ofNullable(personAggregatesRepository.findFirstByPersonId(person.getId()));
        List<Integer> discussLanguages = new ArrayList<>();
        discussLanguages.add(1);
        discussLanguages.add(38);

        Collection<Community> emptyCommunityList = new ArrayList<>();
        Collection<Person> emptyPersonList = new ArrayList<>();
        Collection<Post> emptyPostList = new ArrayList<>();
        Collection<Comment> emptyCommentList = new ArrayList<>();

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

    public void createPerson(Person person) {
        personRepository.save(person);
        PersonAggregates personAggregates = PersonAggregates.builder().person(person).build();
        personAggregatesRepository.save(personAggregates);
        person.setPersonAggregates(personAggregates);
        personCreatedPublisher.publishPersonCreatedEvent(person);
    }

    public Person create(
            String name
    ) throws NoSuchAlgorithmException {
        KeyStore keys = keyService.generate();
        return Person.builder()
                .name(name)
                .password("")
                .instance(localInstanceContext.instance())
                .displayName("")
                .activityPubId("")
                .avatarImageUrl("")
                .bannerImageUrl("")
                .biography("")
                .publicKey(keys.publicKey())
                .privateKey(keys.privateKey())
                .isLocal(true)
                .isBanned(false)
                .build();
    }
}
