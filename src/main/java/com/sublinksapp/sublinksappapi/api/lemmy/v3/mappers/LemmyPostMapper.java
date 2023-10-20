package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LemmyPostMapper {
    @Mapping(target = "url", source = "post.linkUrl")
    @Mapping(target = "thumbnail_url", source = "post.linkThumbnailUrl")
    @Mapping(target = "published", source = "post.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "name", source = "post.title")
    @Mapping(target = "locked", constant = "false")
    @Mapping(target = "local", constant = "true")
    @Mapping(target = "language_id", source = "post.language.id")
    @Mapping(target = "featured_local", source = "post.featured")
    @Mapping(target = "featured_community", source = "post.featuredInCommunity")
    @Mapping(target = "embed_video_url", source = "post.linkVideoUrl")
    @Mapping(target = "embed_title", source = "post.linkTitle")
    @Mapping(target = "embed_description", source = "post.linkDescription")
    @Mapping(target = "community_id", source = "post.community.id")
    @Mapping(target = "body", source = "post.postBody")
    @Mapping(target = "updated", source = "post.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "ap_id", source = "post.activityPubId")
    Post postToPost(com.sublinksapp.sublinksappapi.post.Post post);
}
