package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SubscribedType;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.CommunityAggregates;
import lombok.Builder;

@Builder
public record CommunityView(
        Community community,
        SubscribedType subscribed,
        boolean blocked,
        CommunityAggregates counts
) {
}