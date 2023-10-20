package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ListRegistrationApplications(
        Boolean unread_only,
        Integer page,
        Integer limit
) {
}