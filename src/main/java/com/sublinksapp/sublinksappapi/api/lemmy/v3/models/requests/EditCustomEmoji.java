package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record EditCustomEmoji(
        int id,
        String category,
        String image_url,
        String alt_text,
        Collection<String> keywords,
        String auth
) {
}