package com.sublinks.sublinksapi.api.lemmy.v3.authentication.models;

import lombok.Builder;

@Builder
public record LoginResponse(
    String jwt,
    boolean registration_created,
    boolean verify_email_sent
) {

}