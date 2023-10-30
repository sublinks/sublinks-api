package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkPersonPostRepository extends JpaRepository<LinkPersonPost, Long> {
    Optional<LinkPersonPost> getLinkPersonPostByPostAndPersonAndLinkType(Post post, Person person, LinkPersonPostType type);
}
