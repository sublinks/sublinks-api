package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import java.util.List;
import lombok.Builder;

@Builder
public record CommunityResponse(
    CommunityView community_view,
    List<Long> discussion_languages
) {

}