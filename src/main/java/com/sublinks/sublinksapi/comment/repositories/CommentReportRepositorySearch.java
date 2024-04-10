package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.entities.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.community.entities.Community;
import jakarta.annotation.Nullable;
import java.util.List;

public interface CommentReportRepositorySearch {

  List<CommentReport> allCommentReportsBySearchCriteria(
      CommentReportSearchCriteria commentReportSearchCriteria);

  long countAllCommentReportsByResolvedFalseAndCommunity(@Nullable List<Community> community);

  long countAllCommentReportsByResolvedFalse();

}
