package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetPostResponse(
    PostView post_view,
    CommunityView community_view,
    List<CommunityModeratorView> moderators,
    Set<PostView> cross_posts
) {

}