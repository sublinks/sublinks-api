package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostReportView;
import lombok.Builder;

@Builder
public record PostReportResponse(
        PostReportView post_report_view
) {
}