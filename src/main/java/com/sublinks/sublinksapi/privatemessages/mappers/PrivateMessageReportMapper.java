package com.sublinks.sublinksapi.privatemessages.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models.PrivateMessageReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrivateMessageReportMapper extends
    Converter<com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport, PrivateMessageReport> {

  @Override
  @Mapping(target = "updated", source = "privateMessageReport.updatedAt",
      dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "published", source = "privateMessageReport.createdAt",
      dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
  @Mapping(target = "resolver_id", source = "privateMessageReport.resolver.id")
  @Mapping(target = "creator_id", source = "privateMessageReport.creator.id")
  @Mapping(target = "private_message_id", source = "privateMessageReport.privateMessage.id")
  @Mapping(target = "original_pm_text", source = "privateMessageReport.originalContent")
  PrivateMessageReport convert(
      @Nullable com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport privateMessageReport);
}
