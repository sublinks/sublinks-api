package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record GetPostsResponse(
        Collection<PostView> posts
) {
}