package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetPrivateMessages(
        Boolean unread_only,
        Integer page,
        Integer limit
) {
}