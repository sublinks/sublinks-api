package com.sublinks.sublinksapi.authorization.listeners;

import com.sublinks.sublinksapi.authorization.services.InitialRoleSetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Listens for the application startup event and performs necessary setup tasks.
 */
@Component
@RequiredArgsConstructor
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  private final InitialRoleSetupService initialRoleSetupService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {

    initialRoleSetupService.generateInitialRoles();
  }
}
