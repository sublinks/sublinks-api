package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record MarkPostAsRead(
    Integer post_id,
    Collection<Integer> post_ids,
    Boolean read
) {

}