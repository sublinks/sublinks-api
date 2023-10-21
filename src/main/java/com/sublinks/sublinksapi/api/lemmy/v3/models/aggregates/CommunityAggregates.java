package com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates;

import lombok.Builder;

@Builder
public record CommunityAggregates(
        Long id,
        Long community_id,
        Long subscribers,
        Long posts,
        Long comments,
        String published,
        Long users_active_day,
        Long users_active_week,
        Long users_active_month,
        Long users_active_half_year,
        Long hot_rank
) {
}