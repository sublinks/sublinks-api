package com.sublinks.sublinksapi.person.mappers;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.PersonContext;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.PersonAggregate;
import com.sublinks.sublinksapi.post.dto.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    @Mapping(target = "person", source = "person")
    @Mapping(target = "posts", source = "posts")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "personAggregate", source = "personAggregate")
    @Mapping(target = "discussLanguages", source = "discussLanguages")
    @Mapping(target = "moderates", source = "moderates")
    @Mapping(target = "follows", source = "follows")
    @Mapping(target = "communityBlocks", source = "communityBlocks")
    @Mapping(target = "personBlocks", source = "personBlocks")
    PersonContext PersonToPersonContext(
            Person person,
            Collection<Post> posts,
            Collection<Comment> comments,
            PersonAggregate personAggregate,
            Collection<Integer> discussLanguages,
            Collection<Community> moderates,
            Collection<Community> follows,
            Collection<Community> communityBlocks,
            Collection<Person> personBlocks
    );
}
