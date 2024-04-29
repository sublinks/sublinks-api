package com.sublinks.sublinksapi.api.sublinks.v1.languages.mappers;

import com.sublinks.sublinksapi.api.sublinks.v1.languages.models.LangaugeResponse;
import com.sublinks.sublinksapi.authorization.services.RoleAuthorizingService;
import com.sublinks.sublinksapi.language.entities.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
    RoleAuthorizingService.class})
public abstract class LanguageMapper implements Converter<Language, LangaugeResponse> {

  @Override
  @Mapping(target = "key", source = "language.name")
  @Mapping(target = "name", source = "language.name")
  @Mapping(target = "code", source = "language.code")
  public abstract LangaugeResponse convert(@Nullable Language language);

}
