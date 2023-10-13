package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Site(
        Long id,
        String name,
        String sidebar,
        String published,
        String updated,
        String icon,
        String banner,
        String description,
        String actor_id,
        String last_refreshed_at,
        String inbox_url,
        String public_key,
        Long instance_id
) {
}