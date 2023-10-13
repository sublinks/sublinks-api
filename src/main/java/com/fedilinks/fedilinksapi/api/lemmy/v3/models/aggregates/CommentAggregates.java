package com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates;

import lombok.Builder;

@Builder
public record CommentAggregates(
        Long id,
        Long comment_id,
        Long score,
        Long upvotes,
        Long downvotes,
        String published,
        Long child_count,
        Long hot_rank
) {
}