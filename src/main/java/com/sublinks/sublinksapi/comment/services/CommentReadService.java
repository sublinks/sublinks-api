package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentRead;
import com.sublinks.sublinksapi.comment.repositories.CommentReadRepository;
import com.sublinks.sublinksapi.person.dto.Person;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReadService {

  private final CommentReadRepository commentReadRepository;

  public void markCommentReadByPerson(Comment comment, Person person) {

    Optional<CommentRead> currentCommentRead = commentReadRepository.getCommentReadByCommentAndPerson(
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
