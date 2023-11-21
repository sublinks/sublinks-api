package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record DeletePrivateMessage(
    Integer private_message_id,
    Boolean deleted
) {

}