package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
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