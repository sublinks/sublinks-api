package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers.request;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests.CreatePost;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.instance.Instance;
import com.fedilinks.fedilinksapi.language.Language;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.post.Post;
import com.fedilinks.fedilinksapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreatePostMapper {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "community", source = "community")
    @Mapping(target = "isFeaturedInCommunity", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "isFeatured", constant = "false")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "isRemoved", constant = "false")
    @Mapping(target = "publicKey", source = "keys.publicKey")
    @Mapping(target = "privateKey", source = "keys.privateKey")
    @Mapping(target = "activityPubId", constant = "")
    @Mapping(target = "instance", source = "instance")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "title", source = "createPostForm.name")
    @Mapping(target = "postBody", source = "createPostForm.body")
    @Mapping(target = "isNsfw", source = "createPostForm.nsfw")
    @Mapping(target = "linkThumbnailUrl", constant = "")
    @Mapping(target = "linkDescription", constant = "")
    @Mapping(target = "linkTitle", constant = "")
    @Mapping(target = "linkUrl", constant = "")
    @Mapping(target = "linkVideoUrl", constant = "")
    @Mapping(target = "titleSlug", constant = "")
    Post map(
            CreatePost createPostForm,
            Person person,
            Instance instance,
            Community community,
            Language language,
            KeyStore keys
    );
}
