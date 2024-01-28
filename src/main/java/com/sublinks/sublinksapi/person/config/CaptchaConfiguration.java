package com.sublinks.sublinksapi.person.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CaptchaConfiguration {

  @Value("${sublinks.settings.captcha.max_captchas}")
  private final long maxCaptchas = 100;

  @Value("${sublinks.settings.captcha.rate}")
  private final long rate = 15 * 60;

  @Value("${sublinks.settings.captcha.clearing_rate}")
  private final long clearingRate = 15 * 60;

  @Value("${sublinks.settings.captcha.clearing_older_than}")
  private final long clearingCaptchaOlderThan = 15 * 60;
}
