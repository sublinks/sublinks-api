package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.LemmyPersonMapper;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {LemmyPersonMapper.class, LemmyCommunityMapper.class, LemmyPostMapper.class})
public interface PostViewMapper {
    @Mapping(target = "unread_comments", constant = "0")
    @Mapping(target = "subscribed", source = "subscribedType")
    @Mapping(target = "saved", constant = "false")
    @Mapping(target = "read", constant = "false")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "my_vote", constant = "1")
    @Mapping(target = "creator_blocked", constant = "false")
    @Mapping(target = "creator_banned_from_community", constant = "false")
    @Mapping(target = "creator", source = "person")
    @Mapping(target = "counts", source = "post.postAggregates")
    @Mapping(target = "community", source = "community")
    PostView map(Post post, Community community, SubscribedType subscribedType, Person person);
}
