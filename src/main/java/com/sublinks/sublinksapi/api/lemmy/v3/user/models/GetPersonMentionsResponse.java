package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import java.util.List;
import lombok.Builder;

@Builder
public record GetPersonMentionsResponse(
    List<PersonMentionView> mentions
) {

}