package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record BannedPersonsResponse(
        Collection<PersonView> banned
) {
}