package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.LinkPersonComment;
import com.sublinks.sublinksapi.comment.events.CommentSaveCreatedPublisher;
import com.sublinks.sublinksapi.comment.events.CommentSaverDeletedPublisher;
import com.sublinks.sublinksapi.comment.repositories.ComentSaveRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentSaveService {

  private final ComentSaveRepository comentSaveRepository;
  private final CommentSaveCreatedPublisher commentSaveCreatedPublisher;
  private final CommentSaverDeletedPublisher commentSaverDeletedPublisher;

  public boolean isCommentSavedByPerson(final Comment comment, final Person person) {

    return comentSaveRepository.existsByPersonAndComment(person, comment);
  }

  @Transactional
  public void createCommentSave(final LinkPersonComment linkPersonComment) {

    comentSaveRepository.save(linkPersonComment);
    commentSaveCreatedPublisher.publish(linkPersonComment);
  }

  @Transactional
  public void deleteCommentSave(final LinkPersonComment linkPersonComment) {

    comentSaveRepository.delete(linkPersonComment);
    commentSaverDeletedPublisher.publish(linkPersonComment);
  }
}
