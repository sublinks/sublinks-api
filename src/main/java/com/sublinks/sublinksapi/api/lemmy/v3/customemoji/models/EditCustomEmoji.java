package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record EditCustomEmoji(
    Integer id,
    String category,
    String image_url,
    String alt_text,
    Collection<String> keywords
) {

}