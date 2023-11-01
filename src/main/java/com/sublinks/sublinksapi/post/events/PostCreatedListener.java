package com.sublinks.sublinksapi.post.events;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.services.PostReadService;
import com.sublinks.sublinksapi.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreatedListener implements ApplicationListener<PostCreatedEvent> {
    private final PostReadService postReadService;
    private final PostService postService;

    @Override
    public void onApplicationEvent(PostCreatedEvent event) {
        Person creator = postService.getPostCreator(event.getPost());
        postReadService.markPostReadByPerson(event.getPost(), creator);
    }
}
