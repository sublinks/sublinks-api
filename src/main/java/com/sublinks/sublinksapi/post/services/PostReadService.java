package com.sublinks.sublinksapi.post.services;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostRead;
import com.sublinks.sublinksapi.post.repositories.PostReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostReadService {
    private final PostReadRepository postReadRepository;

    public void markPostReadByPerson(Post post, Person person) {

        Optional<PostRead> currentPostRead = postReadRepository.getPostReadByPostAndPerson(post, person);
        if (currentPostRead.isEmpty()) {
            PostRead postRead = PostRead.builder()
                    .post(post)
                    .person(person)
                    .build();
            postReadRepository.save(postRead);
        }
    }
}
