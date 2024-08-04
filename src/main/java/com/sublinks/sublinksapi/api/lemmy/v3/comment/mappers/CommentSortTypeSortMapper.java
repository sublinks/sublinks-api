package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentSortTypeSortMapper extends
    Converter<SortType, com.sublinks.sublinksapi.comment.enums.CommentSortType> {

  @Override
  @ValueMapping(source = "Hot", target = "Hot")
  @ValueMapping(source = "Active", target = "Hot")
  @ValueMapping(source = "MostComments", target = "Hot")
  @ValueMapping(source = "NewComments", target = "Hot")
  @ValueMapping(source = "TopHour", target = "Top")
  @ValueMapping(source = "TopSixHour", target = "Top")
  @ValueMapping(source = "TopTwelveHour", target = "Top")
  @ValueMapping(source = "TopThreeMonths", target = "Top")
  @ValueMapping(source = "TopSixMonths", target = "Top")
  @ValueMapping(source = "TopNineMonths", target = "Top")
  @ValueMapping(source = "Controversial", target = "Top")
  @ValueMapping(source = "Scaled", target = "Top")
  @ValueMapping(source = "TopDay", target = "Top")
  @ValueMapping(source = "TopWeek", target = "Top")
  @ValueMapping(source = "TopMonth", target = "Top")
  @ValueMapping(source = "TopYear", target = "Top")
  @ValueMapping(source = "TopAll", target = "Top")
  @ValueMapping(source = "New", target = "New")
  @ValueMapping(source = "Old", target = "Old")
  com.sublinks.sublinksapi.comment.enums.CommentSortType convert(@Nullable SortType sortType);
}
