package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record CreatePrivateMessageReport(
        int private_message_id,
        String reason,
        String auth
) {
}