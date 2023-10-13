package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record RegistrationApplication(
        Long id,
        Long local_user_id,
        String answer,
        Long admin_id,
        String deny_reason,
        String published
) {
}