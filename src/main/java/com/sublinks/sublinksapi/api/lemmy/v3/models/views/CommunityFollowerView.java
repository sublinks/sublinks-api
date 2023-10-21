package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record CommunityFollowerView(
        Community community,
        Person follower
) {
}