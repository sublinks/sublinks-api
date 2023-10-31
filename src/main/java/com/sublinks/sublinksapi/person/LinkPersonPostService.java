package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.person.events.LinkPersonPostCreatedPublisher;
import com.sublinks.sublinksapi.person.events.LinkPersonPostDeletedPublisher;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.post.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkPersonPostService {
    private final LinkPersonPostRepository linkPersonPostRepository;
    private final LinkPersonPostCreatedPublisher linkPersonPostCreatedPublisher;
    private final LinkPersonPostDeletedPublisher linkPersonPostDeletedPublisher;
    private final PostLikeService postLikeService;

    @Transactional
    public void createLink(Person person, Post post, LinkPersonPostType type) {

        final LinkPersonPost newLink = LinkPersonPost.builder()
                .post(post)
                .person(person)
                .linkType(type)
                .build();
        if (post.getLinkPersonPost() == null) {
            post.setLinkPersonPost(new HashSet<>());
        }
        if (person.getLinkPersonPost() == null) {
            person.setLinkPersonPost(new HashSet<>());
        }
        post.getLinkPersonPost().add(newLink);
        person.getLinkPersonPost().add(newLink);
        linkPersonPostRepository.save(newLink);
        postLikeService.updateOrCreatePostLikeLike(post, person);
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
