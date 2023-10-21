package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record PostResponse(
        PostView post_view
) {
}