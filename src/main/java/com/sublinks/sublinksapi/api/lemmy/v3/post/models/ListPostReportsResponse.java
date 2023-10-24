package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

import java.util.List;

@Builder
public record ListPostReportsResponse(
        List<PostReportView> post_reports
) {
}