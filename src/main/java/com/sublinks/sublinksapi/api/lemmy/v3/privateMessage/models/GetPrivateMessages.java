package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record GetPrivateMessages(
        Boolean unread_only,
        Integer page,
        Integer limit
) {
}