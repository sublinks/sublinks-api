package com.sublinks.sublinksapi.person.scheduling;

import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.person.config.CaptchaConfiguration;
import com.sublinks.sublinksapi.person.repositories.CaptchaRepository;
import com.sublinks.sublinksapi.person.services.CaptchaService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CaptchaScheduler {

  private final CaptchaConfiguration captchaConfiguration;

  private final CaptchaService captchaService;
  private final CaptchaRepository captchaRepository;
  private final LocalInstanceContext localInstanceContext;

  @Scheduled(fixedRateString = "${sublinks.settings.captcha.rate}", timeUnit = TimeUnit.SECONDS)
  public void generateCachedCaptcha() {

    if (localInstanceContext.instance().getInstanceConfig() == null
        || !localInstanceContext.instance().getInstanceConfig().isCaptchaEnabled()) {
      return;
    }
    if (captchaRepository.countAllByLockedFalse() < captchaConfiguration.getMaxCaptchas()) {
      captchaService.cacheCaptchas();
    }
  }

  @Scheduled(fixedRateString = "${sublinks.settings.captcha.clearing_rate}", timeUnit = TimeUnit.SECONDS)
  public void clearLockedCaptcha() {

    captchaRepository.deleteAll(captchaRepository.findAllByLockedTrueAndUpdatedAtBefore(
        new Date(
            System.currentTimeMillis() - captchaConfiguration.getClearingCaptchaOlderThan() * 1000)
    ));
  }
}
