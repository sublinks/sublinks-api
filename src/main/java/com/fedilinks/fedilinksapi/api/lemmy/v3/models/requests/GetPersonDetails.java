package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPersonDetails(
        Integer person_id,
        String username,
        SortType sort,
        Integer page,
        Integer limit,
        Integer community_id,
        Boolean saved_only,
        String auth
) {
}