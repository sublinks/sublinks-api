package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.event.LinkPersonPostCreatedPublisher;
import com.sublinks.sublinksapi.person.event.LinkPersonPostDeletedPublisher;
import com.sublinks.sublinksapi.post.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class LinkPersonPostService {
    private final LinkPersonPostRepository linkPersonPostRepository;
    private final LinkPersonPostCreatedPublisher linkPersonPostCreatedPublisher;
    private final LinkPersonPostDeletedPublisher linkPersonPostDeletedPublisher;

    public LinkPersonPostService(LinkPersonPostRepository linkPersonPostRepository, LinkPersonPostCreatedPublisher linkPersonPostCreatedPublisher, LinkPersonPostDeletedPublisher linkPersonPostDeletedPublisher) {
        this.linkPersonPostRepository = linkPersonPostRepository;
        this.linkPersonPostCreatedPublisher = linkPersonPostCreatedPublisher;
        this.linkPersonPostDeletedPublisher = linkPersonPostDeletedPublisher;
    }

    @Transactional
    public void createLink(Person person, Post post, LinkPersonPostType type) {

        final LinkPersonPost newLink = LinkPersonPost.builder()
                .post(post)
                .person(person)
                .linkType(type)
                .build();
        person.getLinkPersonPost().add(newLink);
        post.getLinkPersonPost().add(newLink);
        linkPersonPostRepository.save(newLink);
        linkPersonPostCreatedPublisher.publish(newLink);
    }

    @Transactional
    public void removeLink(Person person, Post post, LinkPersonPostType type) {

        Optional<LinkPersonPost> linkPersonPost = linkPersonPostRepository.getLinkPersonPostByPostAndPersonAndLinkType(
                post,
                person,
                type
        );
        if (linkPersonPost.isEmpty()) {
            return;
        }
        person.getLinkPersonPost().removeIf(l -> Objects.equals(l.getId(), linkPersonPost.get().getId()));
        post.getLinkPersonPost().removeIf(l -> Objects.equals(l.getId(), linkPersonPost.get().getId()));
        linkPersonPostRepository.delete(linkPersonPost.get());
        linkPersonPostDeletedPublisher.publish(linkPersonPost.get());
    }
}
