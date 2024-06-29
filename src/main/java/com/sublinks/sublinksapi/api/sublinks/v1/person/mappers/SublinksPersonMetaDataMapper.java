package com.sublinks.sublinksapi.api.sublinks.v1.person.mappers;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonSessionData;
import com.sublinks.sublinksapi.api.sublinks.v1.roles.mappers.SublinksRoleMapper;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.DateUtils;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.entities.PersonMetaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SublinksRoleMapper.class})
public abstract class SublinksPersonMetaDataMapper implements
    Converter<PersonMetaData, PersonSessionData> {

  @Override
  @Mapping(target = "key", source = "metadata.id")
  @Mapping(target = "ipAddress", source = "metadata.ipAddress")
  @Mapping(target = "userAgent", source = "metadata.userAgent")
  @Mapping(target = "active", source = "metadata.active")
  @Mapping(target = "createdAt",
      source = "metadata.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updatedAt",
      source = "metadata.updatedAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  public abstract PersonSessionData convert(
      @Nullable PersonMetaData metadata);

  @Named("personKey")
  String mapPersonKey(Person person) {

    return person.getName() + "@" + person.getInstance()
        .getDomain();
  }
}
