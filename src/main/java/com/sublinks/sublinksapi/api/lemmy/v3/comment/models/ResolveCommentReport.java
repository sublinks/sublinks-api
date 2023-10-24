package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
public record ResolveCommentReport(
        Integer report_id,
        Boolean resolved
) {
}