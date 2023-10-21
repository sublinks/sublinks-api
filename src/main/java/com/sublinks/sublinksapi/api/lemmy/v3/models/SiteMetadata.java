package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record SiteMetadata(
        String title,
        String description,
        String image,
        String embed_video_url
) {
}