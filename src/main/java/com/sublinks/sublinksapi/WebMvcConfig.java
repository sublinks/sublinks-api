package com.sublinks.sublinksapi;

import com.sublinks.sublinksapi.interceptors.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private final RateLimiter rateLimiter;

  public WebMvcConfig(RateLimiter rateLimiter) {
    this.rateLimiter = rateLimiter;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(rateLimiter)
        .excludePathPatterns("/api/v3/site");
  }
}
