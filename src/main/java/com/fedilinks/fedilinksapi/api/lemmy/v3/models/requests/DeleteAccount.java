package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record DeleteAccount(
        String password,
        String auth
) {
}