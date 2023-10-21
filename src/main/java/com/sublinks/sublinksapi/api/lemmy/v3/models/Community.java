package com.sublinks.sublinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Community(
        Long id,
        String name,
        String title,
        String description,
        boolean removed,
        String published,
        String updated,
        boolean deleted,
        boolean nsfw,
        String actor_id,
        boolean local,
        String icon,
        String banner,
        String followers_url,
        String inbox_url,
        boolean hidden,
        boolean posting_restricted_to_mods,
        Long instance_id
) {
}