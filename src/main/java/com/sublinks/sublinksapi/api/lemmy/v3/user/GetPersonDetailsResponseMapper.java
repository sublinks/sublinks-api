package com.sublinks.sublinksapi.api.lemmy.v3.user;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.LemmyCommentMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.models.responses.GetPersonDetailsResponse;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.LemmyPostMapper;
import com.sublinks.sublinksapi.person.PersonContext;
import com.sublinks.sublinksapi.post.Post;
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
            com.sublinks.sublinksapi.api.lemmy.v3.models.Post post = com.sublinks.sublinksapi.api.lemmy.v3.models.Post.builder().build();
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
