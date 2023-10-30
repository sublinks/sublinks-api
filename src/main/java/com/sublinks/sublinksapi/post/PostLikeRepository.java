package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> getPostLikesByPostAndPerson(Post post, Person person);
}
