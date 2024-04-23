package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria;
import java.util.List;

public interface CommentReplyRepositorySearch {

  List<CommentReply> allCommentReplysBySearchCriteria(
      CommentReplySearchCriteria commentReplySearchCriteria);

}
