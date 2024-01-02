package com.sublinks.sublinksapi.api.lemmy.v3.user.scheduling;

import com.sublinks.sublinksapi.api.lemmy.v3.user.repositories.CaptchaRepository;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.CaptchaService;
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

  private final CaptchaService captchaService;
  private final CaptchaRepository captchaRepository;

  @Scheduled(fixedRate=15*60*1000)
  public void generateCachedCaptcha() {

    long quantity = captchaRepository.count();

    if (quantity >= 100) {
      return;
    }

    for (long i=quantity; i<100; i++) {
      captchaService.createCaptcha();
    }
  }

}
