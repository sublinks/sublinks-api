package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record EditCommunity(
        Long community_id,
        String title,
        String description,
        String icon,
        String banner,
        Boolean nsfw,
        Boolean posting_restricted_to_mods,
        Collection<String> discussion_languages
) {
}