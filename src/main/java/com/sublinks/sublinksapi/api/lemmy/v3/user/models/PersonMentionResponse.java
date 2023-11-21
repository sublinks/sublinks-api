package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PersonMentionResponse(
    PersonMentionView person_mention_view
) {

}