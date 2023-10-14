package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTypeMapper {

    ListingType listingTypeToListingType(com.fedilinks.fedilinksapi.enums.ListingType listingType);
}
