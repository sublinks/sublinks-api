package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PersonAggregates(
    Long person_id,
    int post_count,
    int comment_count
) {

}