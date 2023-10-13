package com.fedilinks.fedilinksapi.api.lemmy.v3.builders;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses.GetPersonDetailsResponse;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommentView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.person.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonBuilder {
    private PersonRepository personRepository;

    public PersonBuilder(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public GetPersonDetailsResponse getPersonDetailsResponse() {
        Person person = personRepository.findById((long) 1).get();

        return GetPersonDetailsResponse.builder()
                .person_view(personView(person))
                .moderates(moderates(person))
                .posts(posts(person))
                .comments(comments(person))
                .build();
    }

    public PersonView personView(Person person) {
        return null;
    }

    public Collection<CommunityModeratorView> moderates(Person person) {
        return null;
    }

    public Collection<PostView> posts(Person person) {
        return null;
    }

    public Collection<CommentView> comments(Person person) {
        return null;
    }
}
