package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentSaveRepository extends JpaRepository<LinkPersonComment, Long> {

  List<LinkPersonComment> findAllByPerson(Person person);

  Optional<LinkPersonComment> findFirstByPersonAndComment(Person person, Comment comment);

  List<LinkPersonComment> findAllByComment(Comment comment);


  boolean existsByPersonAndComment(Person person, Comment comment);

}
