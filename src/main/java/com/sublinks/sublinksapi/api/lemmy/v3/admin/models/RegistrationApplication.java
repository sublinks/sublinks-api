package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record RegistrationApplication(
    Long id,
    Long local_user_id,
    String answer,
    Long admin_id,
    String deny_reason,
    String published
) {

}