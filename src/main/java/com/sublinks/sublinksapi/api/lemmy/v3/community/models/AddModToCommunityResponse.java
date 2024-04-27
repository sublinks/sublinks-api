package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import java.util.List;
import lombok.Builder;

/**
 * Represents the response of adding a moderator to a community. This class is immutable and
 * implements the record feature in Java.
 */
@Builder
public record AddModToCommunityResponse(
    List<CommunityModeratorView> moderators
) {

}