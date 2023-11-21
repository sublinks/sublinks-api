package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import java.util.List;
import lombok.Builder;

@Builder
public record AddModToCommunityResponse(
    List<CommunityModeratorView> moderators
) {

}