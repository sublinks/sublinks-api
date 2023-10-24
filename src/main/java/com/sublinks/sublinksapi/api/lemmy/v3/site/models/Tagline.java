package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

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