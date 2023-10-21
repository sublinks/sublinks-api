package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.PersonAggregates;
import lombok.Builder;

@Builder
public record LocalUserView(
        LocalUser local_user,
        Person person,
        PersonAggregates counts
) {
}