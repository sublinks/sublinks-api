package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record PostResponse(
        PostView post_view
) {
}