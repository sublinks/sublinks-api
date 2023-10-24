package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

import java.util.List;

@Builder
public record GetPersonMentionsResponse(
        List<PersonMentionView> mentions
) {
}