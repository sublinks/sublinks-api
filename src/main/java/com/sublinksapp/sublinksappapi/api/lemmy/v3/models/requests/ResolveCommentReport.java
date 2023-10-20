package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ResolveCommentReport(
        int report_id,
        boolean resolved,
        String auth
) {
}