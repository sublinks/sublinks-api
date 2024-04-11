package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostRead;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReadRepository extends JpaRepository<PostRead, Long> {

  Optional<PostRead> getPostReadByPostAndPerson(Post post, Person person);
}
