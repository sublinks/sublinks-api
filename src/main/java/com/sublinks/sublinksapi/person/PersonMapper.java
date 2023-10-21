package com.sublinks.sublinksapi.person;

import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    @Mapping(target = "person", source = "person")
    @Mapping(target = "posts", source = "posts")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "personAggregates", source = "personAggregates")
    @Mapping(target = "discussLanguages", source = "discussLanguages")
    @Mapping(target = "moderates", source = "moderates")
    @Mapping(target = "follows", source = "follows")
    @Mapping(target = "communityBlocks", source = "communityBlocks")
    @Mapping(target = "personBlocks", source = "personBlocks")
    PersonContext PersonToPersonContext(
            Person person,
            Collection<Post> posts,
            Collection<Comment> comments,
            PersonAggregates personAggregates,
            Collection<Integer> discussLanguages,
            Collection<Community> moderates,
            Collection<Community> follows,
            Collection<Community> communityBlocks,
            Collection<Person> personBlocks
    );
}
