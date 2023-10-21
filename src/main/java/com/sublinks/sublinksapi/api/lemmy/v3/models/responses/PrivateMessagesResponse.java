package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PrivateMessageView;
import lombok.Builder;

import java.util.List;

@Builder
public record PrivateMessagesResponse(
        List<PrivateMessageView> private_messages
) {
}