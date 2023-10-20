package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.aggregates;

import lombok.Builder;

@Builder
public record SiteAggregates(
        Long id,
        Long site_id,
        int users,
        int posts,
        int comments,
        int communities,
        int users_active_day,
        int users_active_week,
        int users_active_month,
        int users_active_half_year
) {
}