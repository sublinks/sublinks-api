package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record CreateCommunity(
        String name,
        String title,
        String description,
        String icon,
        String banner,
        Boolean nsfw,
        Boolean posting_restricted_to_mods,
        Collection<String> discussion_languages,
        String auth
) {
}