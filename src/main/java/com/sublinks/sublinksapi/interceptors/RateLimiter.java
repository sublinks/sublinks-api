package com.sublinks.sublinksapi.interceptors;

import com.sublinks.sublinksapi.api.lemmy.v3.authentication.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiter implements HandlerInterceptor {

  private final JwtUtil jwtUtil;
  private final ReactiveRedisTemplate<String, String> redis;
  private String userName;
  @Autowired
  public RateLimiter(
          JwtUtil jwtUtil,
          ReactiveRedisTemplate<String, String> redis
  ) {
    this.redis = redis;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) throws Exception {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      try {
        String token = authHeader.substring(7);
        userName = jwtUtil.extractUsername(token);
      } catch (Exception e) {
        this.redis.opsForValue().increment(request.getRemoteAddr()).subscribe();
        return true;
      }
    } else {
      this.redis.opsForValue().increment(request.getRemoteAddr()).subscribe();
      return true;
    }
    this.redis.opsForValue().increment(userName).subscribe();
    return true;
  }
}
