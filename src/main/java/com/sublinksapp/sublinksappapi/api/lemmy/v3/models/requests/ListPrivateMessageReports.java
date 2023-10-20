package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ListPrivateMessageReports(
        int page,
        int limit,
        boolean unresolved_only,
        String auth
) {
}