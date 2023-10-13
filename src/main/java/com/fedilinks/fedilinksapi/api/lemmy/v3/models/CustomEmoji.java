package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record CustomEmoji(
        Long id,
        Long local_site_id,
        String shortcode,
        String image_url,
        String alt_text,
        String category,
        String published,
        String updated
) {
}