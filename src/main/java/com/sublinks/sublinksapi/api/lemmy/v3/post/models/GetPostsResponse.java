package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record GetPostsResponse(
        Collection<PostView> posts
) {
}