package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTypeMapper extends Converter<com.sublinks.sublinksapi.person.enums.ListingType, ListingType> {

    @Override
    ListingType convert(@Nullable com.sublinks.sublinksapi.person.enums.ListingType listingType);
}
