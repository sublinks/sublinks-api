package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ListPostReports(
        Integer page,
        Integer limit,
        Boolean unresolved_only,
        Integer community_id
) {
}