package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.VoteView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.PersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PersonMapper.class)
public interface CommentVoteViewMapper extends
    Converter<com.sublinks.sublinksapi.comment.entities.CommentLike, VoteView> {

  @Override
  @Mapping(target = "creator", source = "commentLike.person")
  @Mapping(target = "score", expression = "java(commentLike.isUpVote() ? 1 : -1)")
  VoteView convert(@Nullable com.sublinks.sublinksapi.comment.entities.CommentLike commentLike);
}
