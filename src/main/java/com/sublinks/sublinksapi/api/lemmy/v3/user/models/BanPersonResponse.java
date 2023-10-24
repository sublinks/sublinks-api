package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record BanPersonResponse(
        PersonView person_view,
        boolean banned
) {
}