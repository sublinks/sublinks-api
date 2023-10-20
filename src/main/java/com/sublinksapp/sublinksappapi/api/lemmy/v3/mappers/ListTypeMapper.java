package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTypeMapper {

    ListingType listingTypeToListingType(com.sublinksapp.sublinksappapi.person.enums.ListingType listingType);
}
