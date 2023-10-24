package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record ResolvePrivateMessageReport(
        Integer report_id,
        Boolean resolved
) {
}