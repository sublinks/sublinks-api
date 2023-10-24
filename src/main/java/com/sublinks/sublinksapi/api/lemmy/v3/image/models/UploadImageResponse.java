package com.sublinks.sublinksapi.api.lemmy.v3.image.models;

import lombok.Builder;

import java.util.List;

@Builder
public record UploadImageResponse(
        String msg,
        String error,
        List<Image> files
) {
}