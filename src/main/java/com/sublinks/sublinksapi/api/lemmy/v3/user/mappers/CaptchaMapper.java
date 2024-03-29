package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.person.dto.Captcha;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.CaptchaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaptchaMapper extends Converter<Captcha, CaptchaResponse> {

  @Override
  CaptchaResponse convert(@Nullable Captcha captcha);
}
