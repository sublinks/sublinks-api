package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
public record ApproveRegistrationApplication(
    Boolean approved,
    String deny_reason,
    Integer id
) {

}