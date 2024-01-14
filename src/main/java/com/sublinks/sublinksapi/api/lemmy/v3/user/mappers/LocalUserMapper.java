package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import com.sublinks.sublinksapi.person.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public interface LocalUserMapper extends Converter<Person, LocalUser> {

  LocalUser convert(@Nullable Person person);
}
