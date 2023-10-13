package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.LocalUser;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.PersonAggregates;
import lombok.Builder;

@Builder
public record LocalUserView(
        LocalUser local_user,
        Person person,
        PersonAggregates counts
) {
}