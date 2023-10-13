package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonMentionView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetPersonMentionsResponse(
        List<PersonMentionView> mentions
) {
}