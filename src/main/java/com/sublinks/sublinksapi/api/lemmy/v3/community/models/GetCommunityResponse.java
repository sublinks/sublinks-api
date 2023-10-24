package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Site;
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