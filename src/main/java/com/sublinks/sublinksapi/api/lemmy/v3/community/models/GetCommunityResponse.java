package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Site;
import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetCommunityResponse(
    CommunityView community_view,
    Site site,
    List<CommunityModeratorView> moderators,
    List<Long> discussion_languages
) {

}