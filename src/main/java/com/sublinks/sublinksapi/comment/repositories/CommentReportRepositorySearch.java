package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentReport;
import com.sublinks.sublinksapi.comment.models.CommentReportSearchCriteria;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import java.util.List;

public interface CommentReportRepositorySearch {

  List<CommentReport> allCommentReportsBySearchCriteria(CommentReportSearchCriteria commentReportSearchCriteria);
}
