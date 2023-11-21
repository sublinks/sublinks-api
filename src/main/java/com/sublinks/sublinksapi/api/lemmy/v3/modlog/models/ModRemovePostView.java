package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ModRemovePostView(
    ModRemovePost mod_remove_post,
    Post post,
    Community community
) {

}