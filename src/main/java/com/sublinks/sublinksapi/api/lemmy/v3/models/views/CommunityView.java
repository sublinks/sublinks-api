package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.CommunityAggregates;
import lombok.Builder;

@Builder
public record CommunityView(
        Community community,
        SubscribedType subscribed,
        boolean blocked,
        CommunityAggregates counts
) {
}