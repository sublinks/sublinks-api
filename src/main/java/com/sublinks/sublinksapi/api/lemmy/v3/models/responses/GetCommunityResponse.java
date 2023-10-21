package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Site;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetCommunityResponse(
        CommunityView community_view,
        Site site,
        List<CommunityModeratorView> moderators,
        List<String> discussion_languages
) {
}