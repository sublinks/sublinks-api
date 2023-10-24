package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

import java.util.List;

@Builder
public record ListRegistrationApplicationsResponse(
        List<RegistrationApplicationView> registration_applications
) {
}