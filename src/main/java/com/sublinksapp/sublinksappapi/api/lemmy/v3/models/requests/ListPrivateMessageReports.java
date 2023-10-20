package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ListPrivateMessageReports(
        Integer page,
        Integer limit,
        Boolean unresolved_only
) {
}