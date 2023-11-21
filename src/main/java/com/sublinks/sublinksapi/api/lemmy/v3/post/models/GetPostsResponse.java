package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import java.util.Collection;
import lombok.Builder;

@Builder
public record GetPostsResponse(
    Collection<PostView> posts
) {

}