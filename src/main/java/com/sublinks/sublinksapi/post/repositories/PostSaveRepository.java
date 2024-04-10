package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostSave;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSaveRepository extends JpaRepository<PostSave, Long> {

  Optional<PostSave> getPostSaveByPostAndPerson(Post post, Person person);
}
