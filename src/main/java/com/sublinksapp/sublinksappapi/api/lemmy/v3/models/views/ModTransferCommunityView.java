package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Community;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.ModTransferCommunity;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModTransferCommunityView(
        ModTransferCommunity mod_transfer_community,
        Person moderator,
        Community community,
        Person modded_person
) {
}