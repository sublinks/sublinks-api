package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListCommentReportsResponse(
    List<CommentReportView> comment_reports
) {

}