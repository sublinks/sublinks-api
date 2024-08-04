package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentRead;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.shared.RemovedState;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentReadRepository extends JpaRepository<CommentRead, Long> {

  Optional<CommentRead> getCommentReadByCommentAndPerson(Comment comment, Person person);


  @Query("SELECT count(cr) FROM CommentRead cr WHERE cr.person = :person AND cr.comment.post = :post AND cr.comment.removedState = :removedState")
  long countByPersonAndPostAndRemovedState(Person person, Post post,
      RemovedState removedState);
}
