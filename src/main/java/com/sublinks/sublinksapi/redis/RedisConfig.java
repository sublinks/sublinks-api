package com.sublinks.sublinksapi.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  @Bean
  public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
      ReactiveRedisConnectionFactory factory
  ) {

    StringRedisSerializer keySerializer = new StringRedisSerializer();
    StringRedisSerializer valueSerializer = new StringRedisSerializer();
    RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =
        RedisSerializationContext.newSerializationContext(keySerializer);
    RedisSerializationContext<String, String> context = builder.value(valueSerializer).build();

    return new ReactiveRedisTemplate<>(factory, context);
  }
}
