package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.LocalUser;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.aggregates.PersonAggregates;
import lombok.Builder;

@Builder
public record LocalUserView(
        LocalUser local_user,
        Person person,
        PersonAggregates counts
) {
}