package com.sublinks.sublinksapi.api.sublinks.v1.authentication.config;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class represents the configuration for the security of the application. It is responsible
 * for setting up the security filters and rules.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SublinksSecurityConfig {

  private final SublinksJwtFilter sublinksJwtFilter;

  /**
   * Returns a configured SecurityFilterChain object for the application's security.
   *
   * @param http The HttpSecurity object used to configure the security.
   * @return The configured SecurityFilterChain object.
   * @throws Exception If an error occurs during the configuration process.
   */
  @Bean
  public SecurityFilterChain sublinksFilterChain(final HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .securityMatcher("/api/v1/**")
        .authorizeHttpRequests((requests) -> requests.anyRequest()
            .permitAll())
        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

    http.addFilterBefore(sublinksJwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
