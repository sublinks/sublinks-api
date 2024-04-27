package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * Represents an administrative action to purge a community.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgeCommunity(
    Long id,
    Long admin_person_id,
    String reason,
    String when_
) {

}