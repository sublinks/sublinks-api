package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
public record ListRegistrationApplications(
        Boolean unread_only,
        Integer page,
        Integer limit
) {
}