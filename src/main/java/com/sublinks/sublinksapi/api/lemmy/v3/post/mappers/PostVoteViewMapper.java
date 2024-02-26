package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.VoteView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.mappers.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostVoteViewMapper extends
    Converter<com.sublinks.sublinksapi.post.dto.PostLike, VoteView> {

  PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

  @Override
  @Mapping(target = "creator", expression = "java(personMapper.convert(postLike.getPerson()))")
  @Mapping(target = "score", expression = "java(postLike.isUpVote() ? 1 : -1)")
  VoteView convert(@Nullable com.sublinks.sublinksapi.post.dto.PostLike postLike);
}
