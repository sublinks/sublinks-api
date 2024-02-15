package com.sublinks.sublinksapi.person.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UserDataConfig {

  @Value("${sublinks.save_user_ips}")
  private boolean saveUserIps;
}
