package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.ModRemovePost;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModRemovePostView(
        ModRemovePost mod_remove_post,
        Post post,
        Community community
) {
}