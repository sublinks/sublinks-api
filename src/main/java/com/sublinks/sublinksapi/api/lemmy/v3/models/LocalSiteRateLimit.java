package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record LocalSiteRateLimit(
        Long id,
        Long local_site_id,
        int message,
        int message_per_second,
        int post,
        int post_per_second,
        int register,
        int register_per_second,
        int image,
        int image_per_second,
        int comment,
        int comment_per_second,
        int search,
        int search_per_second,
        String published,
        String updated
) {
}