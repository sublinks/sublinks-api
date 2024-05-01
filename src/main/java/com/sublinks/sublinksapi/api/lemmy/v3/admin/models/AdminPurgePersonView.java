package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * The AdminPurgePersonView class represents a view of a person being purged by an admin.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgePersonView(
    AdminPurgePerson admin_purge_person,
    Person admin
) {

}