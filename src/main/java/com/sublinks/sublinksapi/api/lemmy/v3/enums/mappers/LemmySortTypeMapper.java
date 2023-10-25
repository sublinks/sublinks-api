package com.sublinks.sublinksapi.api.lemmy.v3.enums.mappers;

import com.sublinks.sublinksapi.person.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LemmySortTypeMapper {
    @ValueMappings({
            @ValueMapping(target = "Active", source = "Active"),
            @ValueMapping(target = "Hot", source = "Hot"),
            @ValueMapping(target = "New", source = "New"),
            @ValueMapping(target = "Old", source = "Old"),
            @ValueMapping(target = "TopDay", source = "TopDay"),
            @ValueMapping(target = "TopWeek", source = "TopWeek"),
            @ValueMapping(target = "TopMonth", source = "TopMonth"),
            @ValueMapping(target = "TopYear", source = "TopYear"),
            @ValueMapping(target = "TopAll", source = "TopAll"),
            @ValueMapping(target = "MostComments", source = "MostComments"),
            @ValueMapping(target = "NewComments", source = "NewComments"),
            @ValueMapping(target = "TopHour", source = "TopHour"),
            @ValueMapping(target = "TopSixHour", source = "TopSixHour"),
            @ValueMapping(target = "TopTwelveHour", source = "TopTwelveHour"),
            @ValueMapping(target = "TopThreeMonths", source = "TopThreeMonths"),
            @ValueMapping(target = "TopSixMonths", source = "TopSixMonths"),
            @ValueMapping(target = "TopNineMonths", source = "TopNineMonths"),
            @ValueMapping(target = "Controversial", source = "Controversial"),
            @ValueMapping(target = "Scaled", source = "Scaled"),
    })
    SortType map(com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType sortType);
}
