package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GetReportCountResponse(
        int community_id,
        int comment_reports,
        int post_reports,
        int private_message_reports
) {
}