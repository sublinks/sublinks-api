package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.ModAddCommunity;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModAddCommunityView(
        ModAddCommunity mod_add_community,
        Person moderator,
        Community community,
        Person modded_person
) {
}