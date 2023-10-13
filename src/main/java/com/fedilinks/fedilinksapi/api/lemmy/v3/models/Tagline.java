package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Tagline(
        Long id,
        Long local_site_id,
        String content,
        String published,
        String updated
) {
}