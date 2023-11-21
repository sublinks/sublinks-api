package com.sublinks.sublinksapi.api.lemmy.v3.image.models;

import java.util.List;
import lombok.Builder;

@Builder
public record UploadImageResponse(
    String msg,
    String error,
    List<Image> files
) {

}