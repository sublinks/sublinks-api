package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record BlockInstance(
        int instance_id,
        boolean block
) {
}