package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * GetUnreadRegistrationApplicationCountResponse is a record representation of the response from the
 * API endpoint for getting the unread registration applications count.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record GetUnreadRegistrationApplicationCountResponse(
    int registration_applications
) {

}