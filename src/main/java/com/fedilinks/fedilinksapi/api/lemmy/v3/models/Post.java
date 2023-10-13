package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Post(
        Long id,
        String name,
        String url,
        String body,
        Long creator_id,
        Long community_id,
        boolean removed,
        boolean locked,
        String published,
        String updated,
        boolean deleted,
        boolean nsfw,
        String embed_title,
        String embed_description,
        String thumbnail_url,
        String ap_id,
        boolean local,
        String embed_video_url,
        Long language_id,
        boolean featured_community,
        boolean featured_local
) {
}