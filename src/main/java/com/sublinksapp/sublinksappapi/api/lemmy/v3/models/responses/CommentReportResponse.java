package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommentReportView;
import lombok.Builder;

@Builder
public record CommentReportResponse(
        CommentReportView comment_report_view
) {
}