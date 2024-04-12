package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentSave;
import com.sublinks.sublinksapi.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComentSaveRepository extends JpaRepository<CommentSave, Long> {

  List<CommentSave> findAllByPerson(Person person);

  Optional<CommentSave> findFirstByPersonAndCommentId(Person person, Long commentId);

  List<CommentSave> findAllByComment(Comment comment);

}
