package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
public record GetUnreadRegistrationApplicationCountResponse(
        int registration_applications
) {
}