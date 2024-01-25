package com.sublinks.sublinksapi.interceptors;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimiter implements HandlerInterceptor {

  private final ReactiveRedisTemplate<String, String> redis;
  private String userName;
  private final int MAX_REQS = 5;
  private final int LIMITER_DURATION = 10;
  private final int TOO_MANY_REQUESTS = 429;

  @Autowired
  public RateLimiter(ReactiveRedisTemplate<String, String> redis) {
    this.redis = redis;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) {
    try {
      userName = request.getUserPrincipal().getName();
    } catch (Exception e) {}

    String key = userName != null ? userName : request.getRemoteAddr();

    try {
      Long result = this.redis.opsForValue().increment(key).block();

      this.redis.expire(key, Duration.ofSeconds(LIMITER_DURATION)).subscribe();

      if (result != null && result > MAX_REQS) {
        response.setStatus(TOO_MANY_REQUESTS);
        return false;
      }

      return true;
    } catch (Exception e) {

      return true;
    }
  }
}
