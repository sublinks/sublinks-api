package com.sublinks.sublinksapi.api.sublinks.v1.common.mappers;

import com.sublinks.sublinksapi.person.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class SublinksListingTypeMapper implements
    Converter<ListingType, com.sublinks.sublinksapi.api.sublinks.v1.common.enums.ListingType> {

  @Nullable
  @Override
  public com.sublinks.sublinksapi.api.sublinks.v1.common.enums.ListingType convert(
      ListingType listingType)
  {

    return com.sublinks.sublinksapi.api.sublinks.v1.common.enums.ListingType.valueOf(
        listingType.name());
  }


}
