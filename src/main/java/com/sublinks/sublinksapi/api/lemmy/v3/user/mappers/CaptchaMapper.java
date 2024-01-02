package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.user.dto.Captcha;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.GetCaptchaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaptchaMapper {

  @Mapping(target = "ok.png", source = "png")
  @Mapping(target = "ok.wav", source = "wav")
  @Mapping(target = "ok.uuid", source = "uuid")
  GetCaptchaResponse map(Captcha captcha);
}
