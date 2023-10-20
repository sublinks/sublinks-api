package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PrivateMessageView;
import lombok.Builder;

import java.util.List;

@Builder
public record PrivateMessagesResponse(
        List<PrivateMessageView> private_messages
) {
}