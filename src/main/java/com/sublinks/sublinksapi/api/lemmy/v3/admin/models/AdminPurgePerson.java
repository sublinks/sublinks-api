package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * The AdminPurgePerson class represents a record of a person being purged by an admin.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgePerson(
    Long id,
    Long admin_person_id,
    String reason,
    String when_
) {

}