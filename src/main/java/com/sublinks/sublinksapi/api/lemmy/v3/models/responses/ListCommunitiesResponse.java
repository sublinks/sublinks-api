package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record ListCommunitiesResponse(
        Collection<CommunityView> communities
) {
}