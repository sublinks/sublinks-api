package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

/**
 * Represents the response for a purge item operation.
 */
@Builder
public record PurgeItemResponse(
    boolean success
) {

}