package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import com.sublinks.sublinksapi.comment.enums.LinkPersonCommentType;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPersonCommentRepository extends JpaRepository<LinkPersonComment, Long> {


  Optional<LinkPersonComment> getLinkPersonCommentByCommentAndPersonAndLinkType(Comment comment,
      Person person, LinkPersonCommentType type);

  Optional<LinkPersonComment> getLinkPersonCommentByCommentAndPersonAndLinkTypeIn(Comment comment,
      Person person, List<LinkPersonCommentType> types);

  List<LinkPersonComment> getLinkPersonCommentsByPerson(Person person);

  List<LinkPersonComment> getLinkPersonCommentsByComment(Comment comment);

  List<LinkPersonComment> getLinkPersonCommentByCommentAndPerson(Comment comment, Person person);

  List<LinkPersonComment> getLinkPersonCommentByCommentAndLinkTypeIn(Comment comment,
      List<LinkPersonCommentType> types);

  List<LinkPersonComment> getLinkPersonCommentByPersonAndLinkType(Person person,
      LinkPersonCommentType type);

  Optional<LinkPersonComment> deleteLinkPersonCommentByCommentAndPersonAndLinkType(Comment comment,
      Person person, LinkPersonCommentType linkPersonCommentType);

  List<LinkPersonComment> getLinkPersonCommentByPersonAndLinkTypeIn(Person person, List<LinkPersonCommentType> linkPersonCommentTypes);
}
