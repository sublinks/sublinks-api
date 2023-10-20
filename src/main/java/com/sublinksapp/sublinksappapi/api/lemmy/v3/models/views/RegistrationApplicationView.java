package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.LocalUser;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.RegistrationApplication;
import lombok.Builder;

@Builder
public record RegistrationApplicationView(
        RegistrationApplication registration_application,
        LocalUser creator_local_user,
        Person creator,
        Person admin
) {
}