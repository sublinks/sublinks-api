package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortTypeMapper {
    SortType sortTypeToSortType(com.sublinksapp.sublinksappapi.person.enums.SortType sortType);
}
