package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record DeletePrivateMessage(
        Integer private_message_id,
        Boolean deleted
) {
}