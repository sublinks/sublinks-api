package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.CommentSortType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.ListingType;
import lombok.Builder;

@Builder
public record GetComments(
        ListingType type_,
        CommentSortType sort,
        Integer max_depth,
        Integer page,
        Integer limit,
        Integer community_id,
        String community_name,
        Integer post_id,
        Integer parent_id,
        Boolean saved_only
) {
}