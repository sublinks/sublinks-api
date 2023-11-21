package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record ModRemoveCommunityView(
    ModRemoveCommunity mod_remove_community,
    Person moderator,
    Community community
) {

}