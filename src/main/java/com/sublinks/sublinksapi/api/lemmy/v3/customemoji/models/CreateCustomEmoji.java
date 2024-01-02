package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CreateCustomEmoji(
    String category,
    String shortcode,
    String image_url,
    String alt_text,
    Collection<String> keywords) {

}