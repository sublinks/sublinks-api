package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
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