package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record CommentReportResponse(
        CommentReportView comment_report_view
) {
}