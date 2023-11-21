package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ResolvePrivateMessageReport(
    Integer report_id,
    Boolean resolved
) {

}