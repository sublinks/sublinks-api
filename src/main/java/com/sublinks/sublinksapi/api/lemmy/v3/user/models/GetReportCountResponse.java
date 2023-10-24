package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record GetReportCountResponse(
        int community_id,
        int comment_reports,
        int post_reports,
        int private_message_reports
) {
}