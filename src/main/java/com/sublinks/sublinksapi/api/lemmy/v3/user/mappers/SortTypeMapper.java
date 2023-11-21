package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortTypeMapper extends
    Converter<SortType, com.sublinks.sublinksapi.person.enums.SortType> {

  @Override
  com.sublinks.sublinksapi.person.enums.SortType convert(@Nullable SortType sortType);
}
