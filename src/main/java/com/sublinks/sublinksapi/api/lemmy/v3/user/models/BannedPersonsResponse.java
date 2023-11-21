package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import java.util.Collection;
import lombok.Builder;

@Builder
public record BannedPersonsResponse(
    Collection<PersonView> banned
) {

}