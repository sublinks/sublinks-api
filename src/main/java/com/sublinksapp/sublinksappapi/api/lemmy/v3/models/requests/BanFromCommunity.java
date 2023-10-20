package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record BanFromCommunity(
        int community_id,
        int person_id,
        boolean ban,
        boolean remove_data,
        String reason,
        int expires,
        String auth
) {
}