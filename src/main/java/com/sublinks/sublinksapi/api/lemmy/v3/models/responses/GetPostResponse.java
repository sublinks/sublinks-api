package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
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