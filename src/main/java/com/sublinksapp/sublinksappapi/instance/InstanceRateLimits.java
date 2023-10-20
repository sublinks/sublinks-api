package com.sublinksapp.sublinksappapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record InstanceRateLimits(
        @Value("${sublinksapp.rate_limits.message:99}") Integer message,
        @Value("${sublinksapp.rate_limits.message_per_second:999}") Integer messagePerSecond,
        @Value("${sublinksapp.rate_limits.post:99}") Integer post,
        @Value("${sublinksapp.rate_limits.post_per_second:999}") Integer postPerSecond,
        @Value("${sublinksapp.rate_limits.register:99}") Integer register,
        @Value("${sublinksapp.rate_limits.register_per_second:999}") Integer registerPerSecond,
        @Value("${sublinksapp.rate_limits.image:99}") Integer image,
        @Value("${sublinksapp.rate_limits.image_per_second:999}") Integer imagePerSecond,
        @Value("${sublinksapp.rate_limits.comment:99}") Integer comment,
        @Value("${sublinksapp.rate_limits.comment_per_second:999}") Integer commentPerSecond,
        @Value("${sublinksapp.rate_limits.search:99}") Integer search,
        @Value("${sublinksapp.rate_limits.search_per_second:999}") Integer searchPerSecond
) {
}
