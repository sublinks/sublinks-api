package com.sublinks.sublinksapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RateLimiter implements HandlerInterceptor {

  @Value("${LIMITER_MAX_REQS:10}")
  private int MAX_REQS;
  @Value("${LIMITER_DURATION:30}")
  private int LIMITER_DURATION;
  private String userName;
  private final ReactiveRedisTemplate<String, String> redis;
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
  ) throws ResponseStatusException {
    try {
      userName = request.getUserPrincipal().getName();
      // request.getUserPrincipal() will throw if user not authed. We will ignore and just
      // set remote address as key for rate-limiting.
    } catch (Exception ignored) {}

    String key = userName != null ? userName : request.getRemoteAddr();

    try {
      Long result = this.redis.opsForValue().increment(key).block();

      if (result != null && result > MAX_REQS) {
        Duration ttl = this.redis.getExpire(key).block();
        response.setStatus(TOO_MANY_REQUESTS);
          assert ttl != null;
          response.setHeader("Retry-After", String.valueOf(ttl.getSeconds()));
        return false;
      }

      this.redis.expire(key, Duration.ofSeconds(LIMITER_DURATION)).subscribe();
      return true;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "interceptor_error");
    }
  }
}
