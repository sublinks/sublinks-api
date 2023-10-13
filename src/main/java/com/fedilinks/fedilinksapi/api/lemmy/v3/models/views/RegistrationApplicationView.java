package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.LocalUser;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.RegistrationApplication;
import lombok.Builder;

@Builder
public record RegistrationApplicationView(
        RegistrationApplication registration_application,
        LocalUser creator_local_user,
        Person creator,
        Person admin
) {
}