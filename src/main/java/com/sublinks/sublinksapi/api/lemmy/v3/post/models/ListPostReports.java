package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListPostReports(
    Integer page,
    Integer limit,
    Boolean unresolved_only,
    Integer community_id
) {

}