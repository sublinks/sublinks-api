package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record CommunityBlockView(
        Person person,
        Community community
) {
}