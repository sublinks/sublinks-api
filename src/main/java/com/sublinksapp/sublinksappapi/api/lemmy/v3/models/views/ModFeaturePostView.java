package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Community;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.ModFeaturePost;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModFeaturePostView(
        ModFeaturePost mod_feature_post,
        Person moderator,
        Post post,
        Community community
) {
}