package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ListCommentReports(
        int page,
        int limit,
        boolean unresolved_only,
        int community_id,
        String auth
) {
}