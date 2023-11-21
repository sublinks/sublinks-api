package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.person.dto.PersonAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonAggregatesMapper extends Converter<PersonAggregate, PersonAggregates> {

  @Override
  @Mapping(target = "post_score", source = "personAggregate.postScore")
  @Mapping(target = "post_count", source = "personAggregate.postCount")
  @Mapping(target = "person_id", source = "personAggregate.person.id")
  @Mapping(target = "comment_score", source = "personAggregate.commentScore")
  @Mapping(target = "comment_count", source = "personAggregate.commentCount")
  PersonAggregates convert(@Nullable PersonAggregate personAggregate);
}
