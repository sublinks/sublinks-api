package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PostAggregates(
    Long post_id,
    Long comments,
    Long score,
    Long upvotes,
    Long downvotes,
    String published
) {

}