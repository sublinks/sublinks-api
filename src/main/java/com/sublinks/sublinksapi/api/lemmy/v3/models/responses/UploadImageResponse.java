package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Image;
import lombok.Builder;

import java.util.List;

@Builder
public record UploadImageResponse(
        String msg,
        String error,
        List<Image> files
) {
}