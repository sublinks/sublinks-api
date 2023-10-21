package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonMentionView;
import lombok.Builder;

@Builder
public record PersonMentionResponse(
        PersonMentionView person_mention_view
) {
}