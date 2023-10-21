package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Image(
        String file,
        String delete_token
) {
}