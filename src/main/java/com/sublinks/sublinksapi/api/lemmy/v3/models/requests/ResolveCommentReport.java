package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ResolveCommentReport(
        Integer report_id,
        Boolean resolved
) {
}