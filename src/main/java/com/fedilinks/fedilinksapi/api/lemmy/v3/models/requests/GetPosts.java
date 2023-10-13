package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.ListingType;
import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPosts(
        ListingType type_,
        SortType sort,
        int page,
        int limit,
        int community_id,
        String community_name,
        boolean saved_only,
        String auth
) {
}