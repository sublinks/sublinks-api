package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.RegistrationApplicationView;
import lombok.Builder;

@Builder
public record RegistrationApplicationResponse(
        RegistrationApplicationView registration_application
) {
}