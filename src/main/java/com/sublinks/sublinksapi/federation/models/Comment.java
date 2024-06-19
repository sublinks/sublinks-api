package com.sublinks.sublinksapi.federation.models;

import lombok.Builder;

/**
 * Represents a comment in federation queue.
 */
@SuppressWarnings("checkstyle:RecordComponentName")
@Builder
public record Comment(
    String id,
    String url_stub,
    String post_id,
    String author_id,
    Boolean nsfw,
    String content
    //    Published time.Time `json:"published"`
) {

}
