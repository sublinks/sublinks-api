package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GetUnreadRegistrationApplicationCountResponse(
        int registration_applications
) {
}