package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ResolvePostReport(
    Integer report_id,
    Boolean resolved
) {

}