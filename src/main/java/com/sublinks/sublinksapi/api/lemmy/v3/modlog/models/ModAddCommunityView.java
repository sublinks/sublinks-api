package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModAddCommunityView(
    ModAddCommunity mod_add_community,
    Person moderator,
    Community community,
    Person modded_person
) {

}