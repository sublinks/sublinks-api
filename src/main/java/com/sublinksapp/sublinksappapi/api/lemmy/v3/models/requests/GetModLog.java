package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetModLog(
        int mod_person_id,
        int community_id,
        int page,
        int limit,
        String type_,
        int other_person_id,
        String auth
) {
}