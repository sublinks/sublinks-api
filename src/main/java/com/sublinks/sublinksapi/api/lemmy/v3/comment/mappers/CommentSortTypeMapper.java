package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentSortTypeMapper extends Converter<CommentSortType, com.sublinks.sublinksapi.comment.enums.CommentSortType> {
    @Override
    com.sublinks.sublinksapi.comment.enums.CommentSortType convert(@Nullable CommentSortType commentSortType);
}
