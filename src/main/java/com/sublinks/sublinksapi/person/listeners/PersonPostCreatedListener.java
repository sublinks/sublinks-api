package com.sublinks.sublinksapi.person.listeners;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonAggregate;
import com.sublinks.sublinksapi.person.repositories.PersonAggregateRepository;
import com.sublinks.sublinksapi.post.events.PostCreatedEvent;
import com.sublinks.sublinksapi.post.services.PostService;
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
