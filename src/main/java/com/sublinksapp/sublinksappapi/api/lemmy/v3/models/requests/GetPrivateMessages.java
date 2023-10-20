package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetPrivateMessages(
        boolean unread_only,
        int page,
        int limit,
        String auth
) {
}