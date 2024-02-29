package com.sublinks.sublinksapi.person.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CaptchaConfiguration {

  @Value("${sublinks.settings.captcha.max_captchas}")
  private long maxCaptchas;

  @Value("${sublinks.settings.captcha.rate}")
  private long rate;

  @Value("${sublinks.settings.captcha.clearing_rate}")
  private long clearingRate;

  @Value("${sublinks.settings.captcha.clearing_older_than}")
  private long clearingCaptchaOlderThan;
}
