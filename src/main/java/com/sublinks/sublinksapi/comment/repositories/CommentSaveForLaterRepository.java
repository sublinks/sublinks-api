package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentAggregate;
import com.sublinks.sublinksapi.comment.entities.CommentSaveForLater;
import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentSaveForLaterRepository extends JpaRepository<CommentSaveForLater, Long> {

  List<CommentSaveForLater> findAllByPerson(Person person);

  Optional<CommentSaveForLater> findFirstByPersonAndCommentId(Person person, Long commentId);

  List<CommentSaveForLater> findAllByComment(Comment comment);

}
