package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortTypeMapper {
    SortType sortTypeToSortType(com.fedilinks.fedilinksapi.person.enums.SortType sortType);
}
