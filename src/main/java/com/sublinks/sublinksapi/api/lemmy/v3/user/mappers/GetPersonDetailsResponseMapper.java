package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers.CommentAggregatesMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.mappers.PostMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.person.models.PersonContext;
import com.sublinks.sublinksapi.post.dto.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.HashSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyPersonMapper.class, LemmyCommunityMapper.class, CommentAggregatesMapper.class, PostMapper.class})
public abstract class GetPersonDetailsResponseMapper {

    @Mapping(target = "person_view", source = "personContext")
    @Mapping(target = "posts", source = "personContext")
    @Mapping(target = "moderates", source = "personContext")
    @Mapping(target = "comments", source = "personContext")
    public abstract GetPersonDetailsResponse PersonToGetPersonDetailsResponse(PersonContext personContext);

    @Mapping(target = "post", source = "context.posts.post")
    Collection<PostView> personContextToPostViews(PersonContext context) {
        Collection<PostView> views = new HashSet<>();
        // @todo finish mapping
        for (Post postView : context.getPosts()) {
            com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post post = com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post.builder().build();
            PostView view = PostView.builder()
                    .post(post)
                    .build();
        }
        return views;
    }

    Collection<CommunityModeratorView> personContextToCommunityModeratorView(PersonContext value) {
        Collection<CommunityModeratorView> views = new HashSet<>();
        // @todo finish mapping
        return views;
    }

    Collection<CommentView> personContextToCommentView(PersonContext value) {
        Collection<CommentView> views = new HashSet<>();
        // @todo finish mapping
        return views;
    }
}
