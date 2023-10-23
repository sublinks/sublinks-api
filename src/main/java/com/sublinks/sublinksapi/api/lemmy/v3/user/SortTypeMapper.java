package com.sublinks.sublinksapi.api.lemmy.v3.user;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortTypeMapper {
    SortType sortTypeToSortType(com.sublinks.sublinksapi.person.enums.SortType sortType);
}
