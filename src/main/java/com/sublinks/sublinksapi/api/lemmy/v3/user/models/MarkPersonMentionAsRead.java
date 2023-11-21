package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record MarkPersonMentionAsRead(
    Integer person_mention_id,
    Boolean read
) {

}