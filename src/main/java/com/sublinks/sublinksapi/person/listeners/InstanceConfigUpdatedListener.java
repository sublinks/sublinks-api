package com.sublinks.sublinksapi.person.listeners;

import com.sublinks.sublinksapi.instance.events.InstanceConfigUpdatedEvent;
import com.sublinks.sublinksapi.person.services.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class InstanceConfigUpdatedListener implements
    ApplicationListener<InstanceConfigUpdatedEvent> {

  private final CaptchaService captchaService;

  @Override
  public void onApplicationEvent(InstanceConfigUpdatedEvent event) {

    captchaService.recreateAllCaptchas();
  }
}
