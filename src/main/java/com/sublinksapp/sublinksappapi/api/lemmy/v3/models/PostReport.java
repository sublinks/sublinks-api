package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record PostReport(
        Long id,
        Long creator_id,
        Long post_id,
        String original_post_name,
        String original_post_url,
        String original_post_body,
        String reason,
        boolean resolved,
        Long resolver_id,
        String published,
        String updated
) {
}