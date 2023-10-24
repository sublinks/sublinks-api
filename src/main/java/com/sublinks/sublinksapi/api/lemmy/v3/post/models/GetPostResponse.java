package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetPostResponse(
        PostView post_view,
        CommunityView community_view,
        List<CommunityModeratorView> moderators,
        List<PostView> cross_posts
) {
}