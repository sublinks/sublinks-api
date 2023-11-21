package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import java.util.Collection;
import lombok.Builder;

@Builder
public record EditCustomEmoji(
    Integer id,
    String category,
    String image_url,
    String alt_text,
    Collection<String> keywords
) {

}