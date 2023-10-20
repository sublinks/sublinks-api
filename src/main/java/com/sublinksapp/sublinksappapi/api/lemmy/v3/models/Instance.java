package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Instance(
        Long id,
        String domain,
        String published,
        String updated,
        String software,
        String version
) {
}