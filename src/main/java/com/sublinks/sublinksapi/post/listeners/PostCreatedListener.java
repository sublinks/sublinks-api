package com.sublinks.sublinksapi.post.listeners;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.CrossPost;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.events.PostCreatedEvent;
import com.sublinks.sublinksapi.post.repositories.CrossPostRepository;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.post.services.PostReadService;
import com.sublinks.sublinksapi.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostCreatedListener implements ApplicationListener<PostCreatedEvent> {
    private final PostReadService postReadService;
    private final PostService postService;
    private final CrossPostRepository crossPostRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull PostCreatedEvent event) {

        try {
            markPostRead(event.getPost());
        } catch (Exception ignored) {
            // @todo log this
        }
        try {
            createCrossPost(event.getPost());
        } catch (Exception ignored) {
            // @todo log this
        }
    }

    @Transactional
    public void createCrossPost(Post post) {

        if (post.getLinkUrl() == null || post.getLinkUrl().isEmpty()) {
            return;
        }

        String linkMd5Hash = postService.getPostMd5Hash(post);
        if (linkMd5Hash == null || linkMd5Hash.isBlank()) {
            return;
        }

        Optional<CrossPost> crossPost = crossPostRepository.getCrossPostByMd5Hash(linkMd5Hash);
        if (crossPost.isEmpty()) {
            CrossPost newCrossPost = CrossPost.builder()
                    .post(post)
                    .md5Hash(linkMd5Hash)
                    .build();
            crossPostRepository.save(newCrossPost);
        } else {
            post.setCrossPost(crossPost.get());
            postRepository.save(post);
        }
    }

    @Transactional
    public void markPostRead(Post post) {

        Person creator = postService.getPostCreator(post);
        postReadService.markPostReadByPerson(post, creator);
    }
}
