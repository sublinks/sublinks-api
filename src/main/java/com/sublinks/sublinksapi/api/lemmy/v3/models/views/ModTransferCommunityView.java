package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.ModTransferCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModTransferCommunityView(
        ModTransferCommunity mod_transfer_community,
        Person moderator,
        Community community,
        Person modded_person
) {
}