package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgePersonView(
    AdminPurgePerson admin_purge_person,
    Person admin
) {

}