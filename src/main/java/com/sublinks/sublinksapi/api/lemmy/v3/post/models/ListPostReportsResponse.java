package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListPostReportsResponse(
    List<PostReportView> post_reports
) {

}