package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record GetModLog(
        Integer mod_person_id,
        Integer community_id,
        Integer page,
        Integer limit,
        String type_,
        Integer other_person_id
) {
}