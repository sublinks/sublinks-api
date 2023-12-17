package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.person.dto.PersonMention;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMentionMapper extends
    Converter<PersonMention, com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMention> {

  @Override
  @Mapping(target = "id", source = "personMention.id")
  @Mapping(target = "read", source = "personMention.read")
  @Mapping(target = "comment_id", source = "personMention.comment.id")
  @Mapping(target = "recipient_id", source = "personMention.recipient.id")
  @Mapping(target = "published", source = "personMention.createdAt", dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonMention convert(
      @Nullable PersonMention personMention);
}
