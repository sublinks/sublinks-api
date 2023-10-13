package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.ListingType;
import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record ListCommunities(
        ListingType type_,
        SortType sort,
        Boolean show_nsfw,
        Integer page,
        Integer limit,
        String auth
) {
}