package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record PrivateMessageResponse(
        PrivateMessageView private_message_view
) {
}