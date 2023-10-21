package com.sublinks.sublinksapi.api.lemmy.v3.mappers.post;

import com.sublinks.sublinksapi.api.lemmy.v3.models.requests.CreatePost;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.instance.Instance;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.post.Post;
import com.sublinks.sublinksapi.util.KeyStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CreatePostMapper {
    @Mapping(target = "postAggregates", ignore = true)
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
    @Mapping(target = "titleSlug", source = "createPostForm")
    public abstract Post map(
            CreatePost createPostForm,
            Person person,
            Instance instance,
            Community community,
            Language language,
            KeyStore keys
    );

    String mapTitleSlug(CreatePost createPostForm) {
        return createPostForm.name()
                .toLowerCase()
                .replace("\n", " ")
                .replace("[^a-z\\d\\s]", " ")
                .replace("/ +/g", "_");
    }
}
