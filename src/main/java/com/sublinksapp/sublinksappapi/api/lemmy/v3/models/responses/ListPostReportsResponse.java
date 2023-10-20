package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PostReportView;
import lombok.Builder;

import java.util.List;

@Builder
public record ListPostReportsResponse(
        List<PostReportView> post_reports
) {
}