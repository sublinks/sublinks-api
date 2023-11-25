package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.community.dto.Community;
import jakarta.annotation.Nullable;
import java.util.List;

public interface CommentReportRepositorySearch {

  List<CommentReport> allCommentReportsBySearchCriteria(
      CommentReportSearchCriteria commentReportSearchCriteria);

  long countAllCommentReportsByResolvedFalseAndCommunity(@Nullable Community community);

  long countAllCommentReportsByResolvedFalse();
}
