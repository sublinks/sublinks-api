package com.sublinks.sublinksapi.person.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UserDataConfig {

  @Value("${sublinks.save_user_data}")
  private boolean saveUserData;

  @Value("${sublinks.settings.userdata.clear_rate}")
  private long clearRate;

  @Value("${sublinks.settings.userdata.clear_unused_older_than}")
  private long clearOlderThan;
}
