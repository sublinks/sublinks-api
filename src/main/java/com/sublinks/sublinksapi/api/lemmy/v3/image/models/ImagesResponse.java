package com.sublinks.sublinksapi.api.lemmy.v3.image.models;

import java.util.List;
import lombok.Builder;

/**
 * Represents the response from the server containing image data.
 */
@Builder
public record ImagesResponse(
    String msg,
    String error,
    List<Image> files
) {

}