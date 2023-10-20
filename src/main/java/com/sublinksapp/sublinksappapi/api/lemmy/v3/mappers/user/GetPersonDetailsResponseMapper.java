package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.user;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyCommentMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyCommunityMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyPersonMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers.LemmyPostMapper;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses.GetPersonDetailsResponse;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommentView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PostView;
import com.sublinksapp.sublinksappapi.person.PersonContext;
import com.sublinksapp.sublinksappapi.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.HashSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyPersonMapper.class, LemmyCommunityMapper.class, LemmyCommentMapper.class, LemmyPostMapper.class})
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
            com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Post post = com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Post.builder().build();
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
