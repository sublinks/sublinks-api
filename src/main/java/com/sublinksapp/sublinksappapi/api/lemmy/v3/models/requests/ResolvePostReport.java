package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ResolvePostReport(
        Integer report_id,
        Boolean resolved
) {
}