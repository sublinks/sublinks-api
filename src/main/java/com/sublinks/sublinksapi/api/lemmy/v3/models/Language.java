package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Language(
        Long id,
        String code,
        String name
) {
}