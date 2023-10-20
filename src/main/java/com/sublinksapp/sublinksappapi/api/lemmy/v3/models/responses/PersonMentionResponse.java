package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PersonMentionView;
import lombok.Builder;

@Builder
public record PersonMentionResponse(
        PersonMentionView person_mention_view
) {
}