package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SearchType;
import lombok.Builder;

@Builder
public record Search(
        String q,
        int community_id,
        String community_name,
        int creator_id,
        SearchType type_,
        String sort,
        String listing_type,
        String page,
        String limit,
        String auth
) {
}