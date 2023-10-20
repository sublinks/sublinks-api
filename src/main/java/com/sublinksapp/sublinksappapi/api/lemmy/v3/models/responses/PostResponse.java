package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record PostResponse(
        PostView post_view
) {
}