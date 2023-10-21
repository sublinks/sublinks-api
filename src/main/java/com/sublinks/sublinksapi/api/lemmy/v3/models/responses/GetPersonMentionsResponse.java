package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonMentionView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetPersonMentionsResponse(
        List<PersonMentionView> mentions
) {
}