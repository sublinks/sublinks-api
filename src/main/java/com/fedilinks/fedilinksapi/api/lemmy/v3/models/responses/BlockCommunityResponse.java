package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityView;
import lombok.Builder;

@Builder
public record BlockCommunityResponse(
        CommunityView community_view,
        boolean blocked
) {
}