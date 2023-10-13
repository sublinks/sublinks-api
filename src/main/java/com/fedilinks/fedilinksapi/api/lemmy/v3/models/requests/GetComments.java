package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.CommentSortType;
import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.ListingType;
import lombok.Builder;

@Builder
public record GetComments(
        ListingType type_,
        CommentSortType sort,
        int max_depth,
        int page,
        int limit,
        int community_id,
        String community_name,
        int post_id,
        int parent_id,
        boolean saved_only,
        String auth
) {
}