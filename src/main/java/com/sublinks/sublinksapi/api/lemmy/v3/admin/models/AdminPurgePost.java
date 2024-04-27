package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * Represents an administrative action to purge a post.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgePost(
    Long id,
    Long admin_person_id,
    Long community_id,
    String reason,
    String when_
) {

}