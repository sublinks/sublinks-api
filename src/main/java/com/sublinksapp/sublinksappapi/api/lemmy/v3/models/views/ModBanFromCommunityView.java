package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Community;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.ModBanFromCommunity;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModBanFromCommunityView(
        ModBanFromCommunity mod_ban_from_community,
        Person moderator,
        Community community,
        Person banned_person
) {
}