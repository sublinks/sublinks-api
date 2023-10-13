package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record EditCommunity(
        int community_id,
        String title,
        String description,
        String icon,
        String banner,
        boolean nsfw,
        boolean posting_restricted_to_mods,
        Collection<String> discussion_languages,
        String auth
) {
}