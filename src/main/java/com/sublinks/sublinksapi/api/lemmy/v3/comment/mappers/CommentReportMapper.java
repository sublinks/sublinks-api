package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentReportMapper extends
    Converter<com.sublinks.sublinksapi.comment.dto.CommentReport, CommentReport> {

  @Override
  @Mapping(target = "updated", source = "comment.updatedAt",
      dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "published", source = "comment.createdAt",
      dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "creator_id", source = "comment.creator.id")
  @Mapping(target = "resolver_id", source = "comment.resolver.id")
  @Mapping(target = "comment_id", source = "comment.comment.id")
  @Mapping(target = "original_comment_text", source = "comment.originalContent")
  CommentReport convert(@Nullable com.sublinks.sublinksapi.comment.dto.CommentReport comment);
}
