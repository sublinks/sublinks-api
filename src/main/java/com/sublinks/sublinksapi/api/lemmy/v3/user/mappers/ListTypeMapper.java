package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListTypeMapper {

    ListingType listingTypeToListingType(com.sublinks.sublinksapi.person.enums.ListingType listingType);
}
