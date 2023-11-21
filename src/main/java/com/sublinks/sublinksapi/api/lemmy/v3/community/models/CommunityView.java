package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import lombok.Builder;

@Builder
public record CommunityView(
    Community community,
    SubscribedType subscribed,
    boolean blocked,
    CommunityAggregates counts
) {

}