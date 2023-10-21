package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import lombok.Builder;

@Builder
public record BlockCommunityResponse(
        CommunityView community_view,
        boolean blocked
) {
}