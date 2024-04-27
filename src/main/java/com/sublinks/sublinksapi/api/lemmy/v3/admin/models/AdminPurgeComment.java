package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * The AdminPurgeComment class represents a comment purge action performed by an admin.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgeComment(
    Long id,
    Long admin_person_id,
    Long post_id,
    String reason,
    String when_
) {

}