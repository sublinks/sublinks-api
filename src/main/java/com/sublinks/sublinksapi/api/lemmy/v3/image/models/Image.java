package com.sublinks.sublinksapi.api.lemmy.v3.image.models;

import lombok.Builder;

/**
 * Represents an image file with its associated delete token.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record Image(
    String file,
    String delete_token
) {

}