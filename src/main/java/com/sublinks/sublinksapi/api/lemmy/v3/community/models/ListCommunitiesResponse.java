package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import java.util.Collection;
import lombok.Builder;

@Builder
public record ListCommunitiesResponse(
    Collection<CommunityView> communities
) {

}