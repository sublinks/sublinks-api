package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record CreateCustomEmoji(
        String category,
        String shorcode,
        String image_url,
        String alt_text,
        Collection<String> keywords
) {
}