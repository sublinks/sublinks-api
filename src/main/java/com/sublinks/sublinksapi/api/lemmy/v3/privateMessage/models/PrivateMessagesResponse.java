package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

import java.util.List;

@Builder
public record PrivateMessagesResponse(
        List<PrivateMessageView> private_messages
) {
}