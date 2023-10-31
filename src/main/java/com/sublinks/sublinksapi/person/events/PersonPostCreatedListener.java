package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonAggregate;
import com.sublinks.sublinksapi.person.PersonAggregateRepository;
import com.sublinks.sublinksapi.post.PostService;
import com.sublinks.sublinksapi.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class PersonPostCreatedListener implements ApplicationListener<PostCreatedEvent> {
    private final PersonAggregateRepository personAggregateRepository;
    private final PostService postService;

    @Override
    public void onApplicationEvent(PostCreatedEvent event) {
        final Person person = postService.getPostCreator(event.getPost());
        final PersonAggregate personAggregate = person.getPersonAggregate();
        personAggregate.setPostCount(personAggregate.getPostCount() + 1);
        personAggregateRepository.save(personAggregate);
    }
}
