package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.GetPostResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GetPostResponseMapper {
    @Mapping(target = "post_view", source = "postView")
    @Mapping(target = "moderators", source = "moderators")
    @Mapping(target = "cross_posts", source = "crossPosts")
    @Mapping(target = "community_view", source = "communityView")
    GetPostResponse map(
            PostView postView,
            CommunityView communityView,
            List<CommunityModeratorView> moderators,
            List<PostView> crossPosts
    );
}
