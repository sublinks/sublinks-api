package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTypeMapper extends
    Converter<ListingType, com.sublinks.sublinksapi.person.enums.ListingType> {

  @Override
  com.sublinks.sublinksapi.person.enums.ListingType convert(@Nullable ListingType listingType);
}
