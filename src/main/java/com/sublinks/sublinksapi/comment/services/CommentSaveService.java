package com.sublinks.sublinksapi.comment.services;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.entities.CommentSave;
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
  public void createCommentSave(final CommentSave commentSave) {

    comentSaveRepository.save(commentSave);
    commentSaveCreatedPublisher.publish(commentSave);
  }

  @Transactional
  public void deleteCommentSave(final CommentSave commentSave) {

    comentSaveRepository.delete(commentSave);
    commentSaverDeletedPublisher.publish(commentSave);
  }
}
