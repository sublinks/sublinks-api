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
        .addPathPatterns(
            "/api/v3/post*",
            "/api/v3/message*",
            "/api/v3/register*",
            "/api/v3/image*",
            "/api/v3/comment*",
            "/api/v3/search*"
        );
  }
}
