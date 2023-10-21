package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommentReportView;
import lombok.Builder;

@Builder
public record CommentReportResponse(
        CommentReportView comment_report_view
) {
}