package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.ModRemovePost;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModRemovePostView(
        ModRemovePost mod_remove_post,
        Post post,
        Community community
) {
}