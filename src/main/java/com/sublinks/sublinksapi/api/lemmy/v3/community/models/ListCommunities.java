package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record ListCommunities(
        ListingType type_,
        SortType sort,
        Boolean show_nsfw,
        Integer page,
        Integer limit
) {
}