package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record LocalUserView(
    LocalUser local_user,
    Person person,
    PersonAggregates counts
) {

}