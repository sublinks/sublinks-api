package com.sublinks.sublinksapi.api.sublinks.v1.comment.services;

import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.AggregateCommentResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CreateComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.IndexComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.PurgeComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.PinComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.RemoveComment;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.UpdateComment;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionCommentTypes;
import com.sublinks.sublinksapi.authorization.services.RolePermissionService;
import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.comment.repositories.CommentAggregateRepository;
import com.sublinks.sublinksapi.comment.repositories.CommentRepository;
import com.sublinks.sublinksapi.comment.services.CommentService;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.repositories.CommunityRepository;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.language.services.LanguageService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.shared.RemovedState;
import java.util.List;
import java.util.Objects;
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
  private final CommentAggregateRepository commentAggregateRepository;
  private final CommentService commentService;
  private final PostRepository postRepository;
  private final LanguageService languageService;
  private final RolePermissionService rolePermissionService;
  private final LinkPersonCommunityService linkPersonCommunityService;

  /**
   * Retrieves a list of CommentResponse objects based on the provided IndexComment and Person
   * objects.
   *
   * @param indexCommentForm The IndexComment object representing the search criteria for retrieving
   *                         comments.
   * @param person           The Person object representing the user performing the operation.
   * @return A list of CommentResponse objects representing the retrieved comments.
   * @throws ResponseStatusException If the user does not have permission to read comments.
   */
  public List<CommentResponse> index(final IndexComment indexCommentForm, final Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.READ_COMMENTS,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_update_not_permitted"));

    Optional<Comment> parentComment = Optional.empty();
    if (indexCommentForm.postKey() != null) {
      parentComment = commentRepository.findByPath(indexCommentForm.parentCommentKey());
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

    SublinksListingType sublinksListingType = indexCommentForm.listingType();

    if (sublinksListingType == null) {
      if (person.getDefaultListingType() != null) {
        sublinksListingType = conversionService.convert(person.getDefaultListingType(),
            SublinksListingType.class);
      } else {
        sublinksListingType = SublinksListingType.Local;
      }
    }

    CommentSearchCriteria.CommentSearchCriteriaBuilder commentSearchCriteria = CommentSearchCriteria.builder()
        .search(indexCommentForm.search())
        .page(indexCommentForm.page())
        .commentSortType(conversionService.convert(sortType, CommentSortType.class))
        .perPage(indexCommentForm.perPage())
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
   * Retrieves a comment based on the provided key.
   *
   * @param key    The key of the comment to retrieve.
   * @param person The Person object representing the user performing the operation.
   * @return A CommentResponse object representing the retrieved comment.
   * @throws ResponseStatusException If the user does not have permission to read the comment or the
   *                                 comment is not found.
   */
  public CommentResponse show(String key, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.READ_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_view_not_permitted"));

    return commentRepository.findByPath(key)
        .map(comment -> conversionService.convert(comment, CommentResponse.class))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));
  }

  /**
   * Creates a new comment based on the provided data.
   *
   * @param createComment The object representing the data for creating a comment.
   * @param person        The person creating the comment.
   * @return A CommentResponse object representing the created comment.
   * @throws ResponseStatusException If the user does not have permission to create a comment, the
   *                                 post or parent comment is not found, or the language is not
   *                                 found.
   */
  public CommentResponse createComment(CreateComment createComment, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.CREATE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_create_not_permitted"));

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

  /**
   * Updates a comment based on the provided form and person.
   *
   * @param updateCommentForm The UpdateComment object representing the updated data for the
   *                          comment.
   * @param person            The Person object representing the person performing the update.
   * @return A CommentResponse object representing the updated comment.
   * @throws ResponseStatusException If the user does not have permission to update the comment, the
   *                                 comment is not found, or the language is not found.
   */
  public CommentResponse updateComment(UpdateComment updateCommentForm, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.UPDATE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_update_not_permitted"));

    Comment comment = commentRepository.findByPath(updateCommentForm.commentKey())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    if (!Objects.equals(comment.getPerson()
        .getId(), person.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_update_not_permitted");
    }

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

    commentService.updateComment(comment);
    return conversionService.convert(comment, CommentResponse.class);
  }


  /**
   * Removes a comment based on the provided key, comment pin form, and person.
   *
   * @param key               The key of the comment to be removed.
   * @param removeCommentForm The CommentRemove object representing the pin form data.
   * @param person            The Person object representing the user performing the removal.
   * @return A CommentResponse object representing the removed comment.
   * @throws ResponseStatusException If the user does not have permission to pin the comment or
   *                                 the comment is not found.
   */
  public CommentResponse remove(String key, RemoveComment removeCommentForm, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.REMOVE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_remove_not_permitted"));

    Comment comment = commentRepository.findByPath(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    comment.setRemovedState(
        removeCommentForm.remove() != null ? removeCommentForm.remove() ? RemovedState.REMOVED
            : RemovedState.NOT_REMOVED : RemovedState.REMOVED);
    commentService.updateComment(comment);
    // @todo: modlog

    return conversionService.convert(comment, CommentResponse.class);
  }

  /**
   * Deletes a comment based on the provided key, CommentDelete form, and Person.
   *
   * @param key               The key of the comment to be deleted.
   * @param purgeCommentForm The CommentDelete object representing the delete form data.
   * @param person            The Person object representing the user performing the deletion.
   * @return A CommentResponse object representing the deleted comment.
   * @throws ResponseStatusException If the user does not have permission to delete the comment or
   *                                 the comment is not found.
   */
  public void delete(String key, PurgeComment purgeCommentForm, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.DELETE_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_delete_not_permitted"));

    Comment comment = commentRepository.findByPath(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));

    if (!Objects.equals(comment.getPerson()
        .getId(), person.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_delete_not_permitted");
    }

    comment.setDeleted(purgeCommentForm.remove());
    commentService.updateComment(comment);
    // @todo: modlog

  }

  /**
   * Pins or unpins a comment and returns the updated CommentResponse object.
   *
   * @param key            The key of the comment.
   * @param pinCommentForm The CommentPin object representing the pin form data.
   * @param person         The Person object representing the user performing the
   *                       pinning/unpinning.
   * @return A CommentResponse object representing the updated comment.
   * @throws ResponseStatusException If the comment is not found or the user does not have
   *                                 permission to perform the operation.
   */
  public CommentResponse pin(String key, PinComment pinCommentForm, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.MODERATOR_PIN_COMMENT,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_highlight_not_permitted"));

    Comment comment = commentRepository.findByPath(key)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));
    if (linkPersonCommunityService.hasAnyLinkOrAdmin(person, comment.getCommunity(),
        List.of(LinkPersonCommunityType.moderator, LinkPersonCommunityType.owner))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_highlight_not_permitted");
    }

    comment.setFeatured(
        pinCommentForm.pin() != null ? pinCommentForm.pin() : !comment.isFeatured());

    commentService.updateComment(comment);

    return conversionService.convert(comment, CommentResponse.class);
  }

  /**
   * Retrieves a {@link AggregateCommentResponse} based on the provided comment key and person.
   *
   * @param commentKey The key of the comment to retrieve the aggregate for.
   * @param person     The {@link Person} object representing the user performing the operation.
   * @return A {@link AggregateCommentResponse} object representing the retrieved comment aggregate.
   * @throws ResponseStatusException If the comment is not found.
   */
  public AggregateCommentResponse aggregate(String commentKey, Person person) {

    rolePermissionService.isPermitted(person, RolePermissionCommentTypes.READ_COMMENT_AGGREGATE,
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "comment_view_not_permitted"));

    return commentAggregateRepository.findByComment_Path(commentKey)
        .map(commentAggregate -> conversionService.convert(commentAggregate,
            AggregateCommentResponse.class))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment_not_found"));
  }
}
