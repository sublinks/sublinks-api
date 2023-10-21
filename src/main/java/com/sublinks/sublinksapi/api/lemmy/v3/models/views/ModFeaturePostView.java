package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.ModFeaturePost;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModFeaturePostView(
        ModFeaturePost mod_feature_post,
        Person moderator,
        Post post,
        Community community
) {
}