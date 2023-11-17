package com.sublinks.sublinksapi.person.listeners;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.events.CommentCreatedEvent;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonMention;
import com.sublinks.sublinksapi.person.repositories.PersonRepository;
import com.sublinks.sublinksapi.person.services.PersonMentionService;
import com.sublinks.sublinksapi.utils.MentionUtils;
import com.sublinks.sublinksapi.utils.models.Mention;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PersonMentionCommentCreationListener implements ApplicationListener<CommentCreatedEvent> {
    private final MentionUtils mentionUtils;
    private final PersonMentionService personMentionService;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public void onApplicationEvent(CommentCreatedEvent event) {

        Comment comment = event.getComment();
        final List<Mention> mentions = mentionUtils.getPersonMentions(comment.getCommentBody());
        if (mentions != null) {
            for (Mention mention : mentions) {
                Person recipient = personRepository.findOneByName(mention.name());

                if (recipient == null || Objects.equals(recipient, comment.getPerson())) {
                    continue;
                }

                PersonMention personMention = PersonMention.builder()
                        .comment(comment)
                        .recipient(recipient)
                        .build();

                personMentionService.createPersonMention(personMention);
            }
        }

    }
}
