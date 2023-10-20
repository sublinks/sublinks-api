package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.ListingType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPosts(
        ListingType type_,
        SortType sort,
        Integer page,
        Integer limit,
        Integer community_id,
        String community_name,
        Boolean saved_only
) {
}