package com.sublinks.sublinksapi.person.scheduling;

import com.sublinks.sublinksapi.person.config.UserDataConfig;
import com.sublinks.sublinksapi.person.services.UserDataService;
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
public class UserDataScheduler {

  private final UserDataService userDataService;
  private final UserDataConfig userDataConfig;

  @Scheduled(fixedRateString = "${sublinks.settings.userdata.clear_rate}", timeUnit = TimeUnit.SECONDS)
  public void getClearingCaptchaOlderThan() {

    if (userDataConfig.getClearOlderThan() <= 0) {
      return;
    }
    userDataService.clearUserDataBefore(
        new Date(System.currentTimeMillis() - (userDataConfig.getClearOlderThan() * 1000)));
  }
}
