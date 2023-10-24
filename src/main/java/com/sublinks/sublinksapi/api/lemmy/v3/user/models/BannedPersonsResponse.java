package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record BannedPersonsResponse(
        Collection<PersonView> banned
) {
}