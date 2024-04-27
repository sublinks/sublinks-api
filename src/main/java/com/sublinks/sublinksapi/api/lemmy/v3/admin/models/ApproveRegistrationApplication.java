package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * Represents an application to approve a registration.
 */
@Builder
public record ApproveRegistrationApplication(
    Boolean approved,
    String deny_reason,
    Integer id
) {

}