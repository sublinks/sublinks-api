package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListCommentReports(
    Integer page,
    Integer limit,
    Boolean unresolved_only,
    Integer community_id
) {

}