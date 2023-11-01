package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReadRepository extends JpaRepository<PostRead, Long> {
    Optional<PostRead> getPostReadByPostAndPerson(Post post, Person person);
}
