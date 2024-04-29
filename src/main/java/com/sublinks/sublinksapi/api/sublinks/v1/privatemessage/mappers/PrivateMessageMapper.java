package com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.mappers;https://github.com/sublinks/sublinks-docker

import com.sublinks.sublinksapi.api.sublinks.v1.person.mappers.PersonMapper;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.utils.DateUtils;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PersonMapper.class})
public abstract class PrivateMessageMapper implements
    Converter<PrivateMessage, PrivateMessageResponse> {

  @Override
  @Mapping(target = "key", source = "privateMessage.id")
  @Mapping(target = "content", source = "privateMessage.content")
  @Mapping(target = "isLocal", source = "privateMessage.local")
  @Mapping(target = "isDeleted", source = "privateMessage.deleted")
  @Mapping(target = "sender", source = "privateMessage.sender", expression = "java(personMapper.convert(privateMessage.getSender()))")
  @Mapping(target = "recipient", source = "privateMessage.recipient", expression = "java(personMapper.convert(privateMessage.getRecipient()))")
  @Mapping(target = "createdAt", source = "privateMessage.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "updatedAt", source = "privateMessage.updatedAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  public abstract PrivateMessageResponse convert(@Nullable PrivateMessage privateMessage);
}
