package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.models.CommentLikeSearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria;

import java.util.List;

public interface CommentLikeRepositorySearch {

  List<CommentLike> allCommentLikesBySearchCriteria(
      CommentLikeSearchCriteria commentLikeSearchCriteria);

}
