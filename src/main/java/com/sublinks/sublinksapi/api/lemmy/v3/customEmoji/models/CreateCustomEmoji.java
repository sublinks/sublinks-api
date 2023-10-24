package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

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