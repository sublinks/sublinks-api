package com.sublinks.sublinksapi.person.repositories;

import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.entities.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonPostRepository extends JpaRepository<LinkPersonPost, Long> {

  Optional<LinkPersonPost> getLinkPersonPostByPostAndPersonAndLinkType(Post post, Person person,
      LinkPersonPostType linkType);

  Optional<LinkPersonPost> getLinkPersonPostByPostAndPersonAndLinkTypeIn(Post post, Person person,
      List<LinkPersonPostType> linkTypes);

  List<LinkPersonPost> getLinkPersonPostByPerson(Person person);

  List<LinkPersonPost> getLinkPersonPostByPost(Post post);

  List<LinkPersonPost> getLinkPersonPostByPersonAndLinkType(Person person,
      LinkPersonPostType linkType);
}
