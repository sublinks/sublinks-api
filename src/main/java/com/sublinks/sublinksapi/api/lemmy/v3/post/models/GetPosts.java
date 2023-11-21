package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPosts(
    ListingType type_,
    SortType sort,
    Integer page,
    Integer limit,
    Integer community_id,
    String community_name,
    Boolean saved_only,
    Boolean disliked_only,
    String page_cursor
) {

}