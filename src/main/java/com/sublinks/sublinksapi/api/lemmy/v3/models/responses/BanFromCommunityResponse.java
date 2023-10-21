package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonView;
import lombok.Builder;

@Builder
public record BanFromCommunityResponse(
        PersonView person_view,
        boolean banned
) {
}