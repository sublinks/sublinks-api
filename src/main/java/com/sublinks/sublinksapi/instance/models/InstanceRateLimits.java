package com.sublinks.sublinksapi.instance.models;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class InstanceRateLimits {

  @Value("${sublinks.rate_limits.message:99}")
  Integer message;

  @Value("${sublinks.rate_limits.message_per_second:999}")
  Integer messagePerSecond;

  @Value("${sublinks.rate_limits.post:99}")
  Integer post;

  @Value("${sublinks.rate_limits.post_per_second:999}")
  Integer postPerSecond;

  @Value("${sublinks.rate_limits.register:99}")
  Integer register;

  @Value("${sublinks.rate_limits.register_per_second:999}")
  Integer registerPerSecond;

  @Value("${sublinks.rate_limits.image:99}")
  Integer image;

  @Value("${sublinks.rate_limits.image_per_second:999}")
  Integer imagePerSecond;

  @Value("${sublinks.rate_limits.comment:99}")
  Integer comment;

  @Value("${sublinks.rate_limits.comment_per_second:999}")
  Integer commentPerSecond;

  @Value("${sublinks.rate_limits.search:99}")
  Integer search;

  @Value("${sublinks.rate_limits.search_per_second:999}")
  Integer searchPerSecond;
}
