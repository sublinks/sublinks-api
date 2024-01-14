package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper extends
    Converter<Person, com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person> {

  @Override
  com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person convert(@Nullable Person person);
}
