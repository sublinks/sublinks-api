package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.RegistrationApplicationView;
import lombok.Builder;

import java.util.List;

@Builder
public record ListRegistrationApplicationsResponse(
        List<RegistrationApplicationView> registration_applications
) {
}