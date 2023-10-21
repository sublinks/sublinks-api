package com.sublinks.sublinksapi.api.lemmy.v3.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LemmyCommentMapper {
    @Mapping(target = "updated", source = "comment.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "published", source = "comment.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "post_id", source = "comment.post.id")
    @Mapping(target = "local", constant = "true")
    @Mapping(target = "language_id", source = "comment.language.id")
    @Mapping(target = "distinguished", source = "comment.featured")
    @Mapping(target = "creator_id", source = "comment.person.id")
    @Mapping(target = "content", source = "comment.commentBody")
    @Mapping(target = "ap_id", source = "comment.activityPubId")
    Comment commentToComment(com.sublinks.sublinksapi.comment.Comment comment);
}
