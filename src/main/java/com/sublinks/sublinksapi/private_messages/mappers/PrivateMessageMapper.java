package com.sublinks.sublinksapi.private_messages.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models.PrivateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrivateMessageMapper extends
    Converter<com.sublinks.sublinksapi.private_messages.dto.PrivateMessage, PrivateMessage> {

  @Override
  @Mapping(target = "updated", source = "privateMessage.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "published", source = "privateMessage.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "creator_id", source = "privateMessage.sender.id")
  @Mapping(target = "recipient_id", source = "privateMessage.recipient.id")
  @Mapping(target = "local", source = "privateMessage.local")
  @Mapping(target = "deleted", source = "privateMessage.deleted")
  @Mapping(target = "read", source = "privateMessage.read")
  @Mapping(target = "ap_id", source = "privateMessage.activityPubId")
  PrivateMessage convert(
      @Nullable com.sublinks.sublinksapi.private_messages.dto.PrivateMessage privateMessage);
}
