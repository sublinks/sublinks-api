package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Image;
import lombok.Builder;

import java.util.List;

@Builder
public record UploadImageResponse(
        String msg,
        String error,
        List<Image> files
) {
}