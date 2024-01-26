package com.sublinks.sublinksapi.interceptors;

import com.sublinks.sublinksapi.api.lemmy.v3.site.mappers.LocalSiteRateLimitMapper;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimiterUnitTests {
  @Mock
  private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
  @Mock
  private ReactiveValueOperations<String, String> valueOperations;
  @Mock
  private LocalSiteRateLimitMapper limits;
  @Mock
  private LocalInstanceContext localInstanceContext;
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;

  @InjectMocks
  private RateLimiter rateLimiter;

  @BeforeEach
  public void setUp() {
    when(reactiveRedisTemplate.opsForValue()).thenReturn(valueOperations);
  }

  @Test
  public void whenUnderRateLimit_thenAllowRequest() {
    when(request.getUserPrincipal()).thenReturn(() -> "user");
    when(request.getRequestURI()).thenReturn("/api/v3/posts/list");
    when(valueOperations.increment("user")).thenReturn(Mono.just(1L));
    when(valueOperations.get("user")).thenReturn(Mono.just("mock-user"));
    when(reactiveRedisTemplate.expire("user", Duration.ofSeconds(30))).thenReturn(Mono.just(true));

    boolean result = rateLimiter.preHandle(request, response, null);

    assertTrue(result);
    verify(response, never()).setStatus(anyInt());
  }
}
