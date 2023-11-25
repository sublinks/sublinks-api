package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentReply;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReplySearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.community.dto.Community;
import java.util.List;

public interface CommentReplyRepositorySearch {

  List<CommentReply> allCommentReplysBySearchCriteria(
      CommentReplySearchCriteria commentReplySearchCriteria);

}
