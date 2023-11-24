package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentReplyMapper extends
    Converter<com.sublinks.sublinksapi.comment.dto.CommentReply, CommentReply> {

  @Override
  @Mapping(target = "published", source = "commentReply.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "comment_id", source = "commentReply.comment.id")
  @Mapping(target = "recipient_id", source = "commentReply.recipient.id")
  @Mapping(target = "read", source = "commentReply.isRead")
  CommentReply convert(@Nullable com.sublinks.sublinksapi.comment.dto.CommentReply commentReply);
}
