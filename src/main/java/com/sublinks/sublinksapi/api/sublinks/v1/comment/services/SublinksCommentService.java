package com.sublinks.sublinksapi.api.sublinks.v1.comment.services;

import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CreateComment;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.shared.RemovedState;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
public class SublinksCommentService {

  private final LocalInstanceContext localInstanceContext;
  private final ConversionService conversionService;
  private final LanguageRepository languageRepository;
  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final PostRepository postRepository;

  public CommentResponse createComment(CreateComment createComment, Person person) {

    final Post parentPost = postRepository.findByTitleSlugAndRemovedStateIs(createComment.postKey(),
            RemovedState.NOT_REMOVED)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_not_found"));

    final Comment parentComment = commentRepository.findByPath(createComment.parentKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    Language language;
    try {
      language = languageRepository.findLanguageByCode(createComment.languageKey()
          .orElse("und"));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found");
    }
    Comment comment = Comment.builder()
        .commentBody(createComment.body())
        .person(person)
        .post(parentPost)
        .path(parentComment.getPath())
        .language(language)
        .build();

    commentService.createComment(comment, parentComment);
    return conversionService.convert(comment, CommentResponse.class);
  }


}
