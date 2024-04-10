package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.entities.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonPostRepository extends JpaRepository<LinkPersonPost, Long> {

  Optional<LinkPersonPost> getLinkPersonPostByPostAndPersonAndLinkType(Post post, Person person,
      LinkPersonPostType type);
}
