package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.CaptchaResponse;
import com.sublinks.sublinksapi.person.entities.Captcha;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaptchaMapper extends Converter<Captcha, CaptchaResponse> {

  @Override
  CaptchaResponse convert(@Nullable Captcha captcha);
}
