package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import lombok.Builder;

@Builder
public record BlockPersonResponse(
        PersonView person_view,
        boolean blocked
) {
}