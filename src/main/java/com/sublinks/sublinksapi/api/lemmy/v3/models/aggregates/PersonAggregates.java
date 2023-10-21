package com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates;

import lombok.Builder;

@Builder
public record PersonAggregates(
        Long id,
        Long person_id,
        int post_count,
        int post_score,
        int comment_count,
        int comment_score
) {
}