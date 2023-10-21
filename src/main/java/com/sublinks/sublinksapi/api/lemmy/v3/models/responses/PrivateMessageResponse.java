package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PrivateMessageView;
import lombok.Builder;

@Builder
public record PrivateMessageResponse(
        PrivateMessageView private_message_view
) {
}