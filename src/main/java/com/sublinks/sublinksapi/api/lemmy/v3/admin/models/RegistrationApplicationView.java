package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * Represents a view of a registration application.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record RegistrationApplicationView(
    RegistrationApplication registration_application,
    LocalUser creator_local_user,
    Person creator,
    Person admin
) {

}