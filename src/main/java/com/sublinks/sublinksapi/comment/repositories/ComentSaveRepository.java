package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentSave;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentSaveRepository extends JpaRepository<CommentSave, Long> {

  List<CommentSave> findAllByPerson(Person person);

  Optional<CommentSave> findFirstByPersonAndComment(Person person, Comment comment);

  List<CommentSave> findAllByComment(Comment comment);


  boolean existsByPersonAndComment(Person person, Comment comment);

}
