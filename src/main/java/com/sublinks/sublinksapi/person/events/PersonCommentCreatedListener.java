package com.sublinks.sublinksapi.person.events;

import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.person.PersonAggregate;
import com.sublinks.sublinksapi.person.PersonAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class PersonCommentCreatedListener implements ApplicationListener<CommentCreatedEvent> {
    private final PersonAggregateRepository personAggregateRepository;

    @Override
    public void onApplicationEvent(CommentCreatedEvent event) {
        final PersonAggregate personAggregate = event.getComment().getPerson().getPersonAggregate();
        personAggregate.setCommentCount(personAggregate.getCommentCount() + 1);
        personAggregateRepository.save(personAggregate);
    }
}
