package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record PersonView(
    Person person,
    PersonAggregates counts,
    boolean is_admin
) {

}