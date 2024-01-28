package com.sublinks.sublinksapi.api.lemmy.v3.user.scheduling;

import com.sublinks.sublinksapi.person.repositories.CaptchaRepository;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CaptchaScheduler {

  private final CaptchaService captchaService;
  private final CaptchaRepository captchaRepository;



  @Value("${sublinks.settings.captcha.max_captchas}")
  private final long maxCaptchas = 100;

  @Value("${sublinks.settings.captcha.rate}")
  private final long rate = 15*60;

  @Value("${sublinks.settings.captcha.clearing_rate}")
  private final long clearingRate = 15*60;

  @Value("${sublinks.settings.captcha.clearing_older_than}")
  private final long clearingCaptchaOlderThan = 15*60;


  @Scheduled(fixedRate=rate, timeUnit = TimeUnit.SECONDS)
  public void generateCachedCaptcha() {

    long quantity = captchaRepository.countAllByLockedFalse();

    if (quantity >= maxCaptchas) {
      return;
    }

    for (long i=quantity; i<100; i++) {
      captchaService.createCaptcha();
    }
  }

  @Scheduled(fixedRate=clearingRate, timeUnit = TimeUnit.SECONDS)
  public void clearLockedCaptcha() {
      captchaRepository.deleteAll(captchaRepository.findAllByLockedTrueAndUpdatedAtBefore(
              new Date(System.currentTimeMillis() - clearingCaptchaOlderThan*1000)
      ));
  }
}
