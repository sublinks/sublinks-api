package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.AdminPurgePerson;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record AdminPurgePersonView(
        AdminPurgePerson admin_purge_person,
        Person admin
) {
}