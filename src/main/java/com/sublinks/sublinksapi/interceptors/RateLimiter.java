package com.sublinks.sublinksapi.interceptors;

import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.LocalSiteRateLimitMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.LocalSiteRateLimit;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Component
public class RateLimiter implements HandlerInterceptor {
  private String userName;
  private final ReactiveRedisTemplate<String, String> redis;
  private final LocalSiteRateLimitMapper limits;
  private final LocalInstanceContext localInstanceContext;

  private int maxReqs = 0;
  private int duration = 0;

  @Autowired
  public RateLimiter(
      ReactiveRedisTemplate<String, String> redis,
      LocalSiteRateLimitMapper limits,
      LocalInstanceContext localInstanceContext
  ) {
    this.redis = redis;
    this.limits = limits;
    this.localInstanceContext = localInstanceContext;
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



    try {
      String reqPath = request.getRequestURI();
      LocalSiteRateLimit rateLimit = limits.convert(localInstanceContext);
      setRateLimitParameters(rateLimit, reqPath);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rate_limiter_error");
    }

    String key = userName != null ? userName : request.getRemoteAddr();

    try {
      return getInstanceRateLimiting(key, maxReqs, duration, response);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rate_limiter_error");
    }
  }

  private void setRateLimitParameters(LocalSiteRateLimit rateLimit, String reqPath)
      throws Exception {
    if (rateLimit == null) {
      throw new Exception("rate limit configuration is null");
    }

    if (reqPath.startsWith("/api/v3/post")) {
      this.maxReqs = rateLimit.post_per_second();
      this.duration = rateLimit.post() * rateLimit.post_per_second();
    }
    if (reqPath.startsWith("/api/v3/message")) {
      this.maxReqs = rateLimit.message_per_second();
      this.duration = rateLimit.message() * rateLimit.message_per_second();
    }
    if (reqPath.startsWith("/api/v3/register")) {
      this.maxReqs = rateLimit.register_per_second();
      this.duration = rateLimit.register() * rateLimit.register_per_second();
    }
    if (reqPath.startsWith("/api/v3/image")) {
      this.maxReqs = rateLimit.image_per_second();
      this.duration = rateLimit.image() * rateLimit.image_per_second();
    }
    if (reqPath.startsWith("/api/v3/comment")) {
      this.maxReqs = rateLimit.comment_per_second();
      this.duration = rateLimit.comment() * rateLimit.comment_per_second();
    }
    if (reqPath.startsWith("/api/v3/search")) {
      this.maxReqs = rateLimit.search_per_second();
      this.duration = rateLimit.search() * rateLimit.search_per_second();
    }
  }

  private boolean getInstanceRateLimiting(String key, Integer maxReqs, Integer duration, HttpServletResponse response)
      throws Exception {
    Long result = redis.opsForValue().increment(key).block();

    if (result != null && result > maxReqs) {
      Duration ttl = redis.getExpire(key).block();

      if (ttl == null) {
        throw new Exception("there was a problem fetching the ttl from redis");
      }

      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.setHeader("Retry-After", String.valueOf(ttl.getSeconds()));

      return false;
    }

    redis.expire(key, Duration.ofSeconds(duration)).subscribe();
    response.setHeader("X-RateLimit-Limit", maxReqs.toString());
    response.setHeader("X-RateLimit-Remaining", String.valueOf(maxReqs - (result == null ? 0 : result)));
    response.setHeader("X-RateLimit-Reset", String.valueOf((Instant.now().getEpochSecond() + duration)));
    return true;
  }
}
