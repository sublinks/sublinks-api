package com.sublinks.sublinksapi.person.scheduling;

import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.repositories.CaptchaRepository;
import com.sublinks.sublinksapi.person.services.CaptchaService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CaptchaScheduler {

  private final CaptchaService captchaService;
  private final CaptchaRepository captchaRepository;
  private final LocalInstanceContext localInstanceContext;


  @Value("${sublinks.settings.captcha.max_captchas}")
  private final long maxCaptchas = 100;

  @Value("${sublinks.settings.captcha.rate}")
  private final long rate = 15 * 60;

  @Value("${sublinks.settings.captcha.clearing_rate}")
  private final long clearingRate = 15 * 60;

  @Value("${sublinks.settings.captcha.clearing_older_than}")
  private final long clearingCaptchaOlderThan = 15 * 60;


  @Scheduled(fixedRate = rate, timeUnit = TimeUnit.SECONDS)
  public void generateCachedCaptcha() {

    if (localInstanceContext.instance().getInstanceConfig() == null
        || !localInstanceContext.instance().getInstanceConfig().isCaptchaEnabled()) {
      return;
    }

    long quantity = captchaRepository.countAllByLockedFalse();

    if (quantity >= maxCaptchas) {
      return;
    }

    for (long i = quantity; i < 100; i++) {
      captchaService.createCaptcha();
    }
  }

  @Scheduled(fixedRate = clearingRate, timeUnit = TimeUnit.SECONDS)
  public void clearLockedCaptcha() {

    captchaRepository.deleteAll(captchaRepository.findAllByLockedTrueAndUpdatedAtBefore(
        new Date(System.currentTimeMillis() - clearingCaptchaOlderThan * 1000)
    ));
  }
}
