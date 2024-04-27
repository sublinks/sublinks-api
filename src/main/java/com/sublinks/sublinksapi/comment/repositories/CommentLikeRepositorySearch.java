package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentLike;
import com.sublinks.sublinksapi.comment.models.CommentLikeSearchCriteria;
import java.util.List;

public interface CommentLikeRepositorySearch {

  List<CommentLike> allCommentLikesBySearchCriteria(
      CommentLikeSearchCriteria commentLikeSearchCriteria);

}
