package com.sublinks.sublinksapi.api.lemmy.v3.authentication.models;

import lombok.Builder;

/**
 * This class represents the response for a login operation.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record LoginResponse(
    String jwt,
    boolean registration_created,
    boolean verify_email_sent
) {

}