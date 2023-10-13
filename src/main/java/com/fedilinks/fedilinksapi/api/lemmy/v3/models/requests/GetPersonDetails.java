package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPersonDetails(
        int person_id,
        String username,
        SortType sort,
        int page,
        int limit,
        int community_id,
        boolean saved_only,
        String auth
) {
}