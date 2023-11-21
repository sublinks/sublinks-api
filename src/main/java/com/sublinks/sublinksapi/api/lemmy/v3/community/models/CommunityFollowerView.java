package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record CommunityFollowerView(
    Community community,
    Person follower
) {

}