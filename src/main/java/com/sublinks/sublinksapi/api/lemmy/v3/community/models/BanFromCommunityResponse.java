package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import lombok.Builder;

@Builder
public record BanFromCommunityResponse(
    PersonView person_view,
    boolean banned
) {

}