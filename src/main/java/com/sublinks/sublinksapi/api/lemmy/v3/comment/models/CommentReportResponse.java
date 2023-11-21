package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CommentReportResponse(
    CommentReportView comment_report_view
) {

}