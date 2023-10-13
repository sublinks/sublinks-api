package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record LoginResponse(
        String jwt,
        boolean registration_created,
        boolean verify_email_sent
) {
}