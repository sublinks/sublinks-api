package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
public record PostAggregates(
    Long id,
    Long post_id,
    Long comments,
    Long score,
    Long upvotes,
    Long downvotes,
    String published,
    String newest_comment_time_necro,
    String newest_comment_time,
    boolean featured_community,
    boolean featured_local,
    Long hot_rank,
    Long hot_rank_active
) {

}