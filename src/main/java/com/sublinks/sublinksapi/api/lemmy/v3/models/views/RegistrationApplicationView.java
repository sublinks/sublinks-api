package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.RegistrationApplication;
import lombok.Builder;

@Builder
public record RegistrationApplicationView(
        RegistrationApplication registration_application,
        LocalUser creator_local_user,
        Person creator,
        Person admin
) {
}