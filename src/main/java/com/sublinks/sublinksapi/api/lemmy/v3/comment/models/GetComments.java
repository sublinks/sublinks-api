package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
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
    Boolean saved_only,
    Boolean liked_only,
    Boolean disliked_only
) {

}