package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record ModFeaturePostView(
    ModFeaturePost mod_feature_post,
    Person moderator,
    Post post,
    Community community
) {

}