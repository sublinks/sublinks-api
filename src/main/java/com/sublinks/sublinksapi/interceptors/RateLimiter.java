package com.sublinks.sublinksapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiter implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) throws Exception {
    String ipAddress = request.getRemoteAddr();
    System.out.println("IP: " + ipAddress);
    return true;
  }
}
