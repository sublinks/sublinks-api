package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonView;
import lombok.Builder;

@Builder
public record BanPersonResponse(
        PersonView person_view,
        boolean banned
) {
}