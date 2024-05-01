package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * Represents a response for a registration application.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record RegistrationApplicationResponse(
    RegistrationApplicationView registration_application
) {

}