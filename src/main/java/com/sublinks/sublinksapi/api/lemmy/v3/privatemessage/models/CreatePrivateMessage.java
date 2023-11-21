package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CreatePrivateMessage(
    String content,
    Integer recipient_id
) {

}