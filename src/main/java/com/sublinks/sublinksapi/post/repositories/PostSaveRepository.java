package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostSaveRepository extends JpaRepository<PostSave, Long> {
    Optional<PostSave> getPostSaveByPostAndPerson(Post post, Person person);
}
