package com.sublinks.sublinksapi.api.sublinks.v1.comment.services;

import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.IndexComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.UpdateComment;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.language.services.LanguageService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.shared.RemovedState;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksCommentService {

  private final LocalInstanceContext localInstanceContext;
  private final ConversionService conversionService;
  private final LanguageRepository languageRepository;
  private final CommunityRepository communityRepository;
  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final PostRepository postRepository;
  private final LanguageService languageService;

  /**
   * Performs a search for comments based on the provided criteria.
   *
   * @param indexCommentForm The object representing the search criteria for comments.
   * @param person           The person performing the search.
   * @return A CommentResponse object representing the search results.
   */
  public List<CommentResponse> index(IndexComment indexCommentForm, Person person) {

    Optional<Comment> parentComment = Optional.empty();
    if (indexCommentForm.postKey() != null) {
      parentComment = commentRepository.findByPath(indexCommentForm.postKey());
    }

    Optional<Community> community = Optional.empty();

    if (indexCommentForm.communityKey() != null) {
      community = communityRepository.findCommunityByTitleSlug(indexCommentForm.communityKey());
    }

    Optional<Post> post = Optional.empty();

    if (indexCommentForm.postKey() != null) {
      post = postRepository.findByTitleSlug(indexCommentForm.postKey());
    }

    SortType sortType = indexCommentForm.sortType();
    if (sortType == null) {
      if (person.getDefaultSortType() != null) {
        sortType = conversionService.convert(person.getDefaultSortType(), SortType.class);
      } else {
        sortType = SortType.New;
      }
    }

    SublinksListingType sublinksListingType = indexCommentForm.sublinksListingType();

    if (sublinksListingType == null) {
      if (person.getDefaultListingType() != null) {
        sublinksListingType = conversionService.convert(person.getDefaultListingType(),
            SublinksListingType.class);
      } else {
        sublinksListingType = SublinksListingType.Local;
      }
    }

    CommentSearchCriteria.CommentSearchCriteriaBuilder commentSearchCriteria = CommentSearchCriteria.builder()
        .page(indexCommentForm.page())
        .commentSortType(conversionService.convert(sortType, CommentSortType.class))
        .perPage(indexCommentForm.limit())
        .savedOnly(indexCommentForm.savedOnly())
        .listingType(conversionService.convert(sublinksListingType, ListingType.class))
        .community(community.orElse(null))
        .parent(parentComment.orElse(null))
        .post(post.orElse(null))
        .person(person);

    return commentRepository.allCommentsBySearchCriteria(commentSearchCriteria.build())
        .stream()
        .map(comment -> conversionService.convert(comment, CommentResponse.class))
        .toList();
  }

  /**
   * Creates a comment based on the provided information.
   *
   * @param createComment The object representing the data of the comment to be created.
   * @param person        The person who is creating the comment.
   * @return A CommentResponse object representing the created comment.
   * @throws ResponseStatusException If the post, comment, or language is not found.
   */
  public CommentResponse createComment(CreateComment createComment, Person person) {

    final Post parentPost = postRepository.findByTitleSlugAndRemovedStateIs(createComment.postKey(),
            RemovedState.NOT_REMOVED)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_not_found"));

    final Comment parentComment = commentRepository.findByPath(createComment.parentKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    Language language;
    try {
      language = languageRepository.findLanguageByCode(
          createComment.languageKey() != null ? createComment.languageKey()
              : languageService.getLanguageOfCommunityOrUndefined(parentPost.getCommunity())
                  .getCode());
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

  public CommentResponse updateComment(UpdateComment updateCommentForm, Person person) {

    Comment comment = commentRepository.findByPath(updateCommentForm.commentKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    if (updateCommentForm.featured() != null) {
      comment.setFeatured(updateCommentForm.featured());
    }
    if (updateCommentForm.body() != null) {
      comment.setCommentBody(updateCommentForm.body());
    }
    if (updateCommentForm.languageKey() != null) {
      Language language;
      try {
        language = languageRepository.findLanguageByCode(updateCommentForm.languageKey());
      } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "language_not_found");
      }
      comment.setLanguage(language);
    }

    commentRepository.save(comment);
    return conversionService.convert(comment, CommentResponse.class);
  }
}
