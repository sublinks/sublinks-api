package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.comment.Comment;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.post.Post;
import com.fedilinks.fedilinksapi.util.KeyService;
import com.fedilinks.fedilinksapi.util.KeyStore;
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

    private final PersonAggregatesRepository personAggregatesRepository;

    public PersonService(KeyService keyService, PersonMapper personMapper, PersonAggregatesRepository personAggregatesRepository) {
        this.keyService = keyService;
        this.personMapper = personMapper;
        this.personAggregatesRepository = personAggregatesRepository;
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
                personAggregates.orElse(PersonAggregates.builder().personId(person.getId()).build()),
                discussLanguages,
                emptyCommunityList,
                emptyCommunityList,
                emptyCommunityList,
                emptyPersonList
        );
    }

    public Person create(
            String name
    ) throws NoSuchAlgorithmException {
        KeyStore keys = keyService.generate();
        return Person.builder()
                .name(name)
                .password("")
                .instanceId((long) 1)
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
