package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModBanFromCommunityView(
    ModBanFromCommunity mod_ban_from_community,
    Person moderator,
    Community community,
    Person banned_person
) {

}