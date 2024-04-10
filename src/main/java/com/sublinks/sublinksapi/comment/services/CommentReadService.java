package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentRead;
import com.sublinks.sublinksapi.comment.repositories.CommentReadRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReadService {

  private final CommentReadRepository commentReadRepository;

  /**
   * Marks a comment as read by a specific person. If the comment has already been
   * marked as read by this person, no further action is taken.
   *
   * @param comment The comment that is being marked as read.
   * @param person  The person who has read the comment.
   */
  public void markCommentReadByPerson(Comment comment, Person person) {

    Optional<CommentRead> currentCommentRead
        = commentReadRepository.getCommentReadByCommentAndPerson(
        comment, person);
    if (currentCommentRead.isEmpty()) {
      CommentRead commentRead = CommentRead.builder()
          .comment(comment)
          .person(person)
          .build();
      commentReadRepository.save(commentRead);
    }
  }
}
