package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Community;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.ModHideCommunity;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModHideCommunityView(
        ModHideCommunity mod_hide_community,
        Person admin,
        Community community
) {
}