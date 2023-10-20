package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record BlockInstance(
        Integer instance_id,
        Boolean block
) {
}