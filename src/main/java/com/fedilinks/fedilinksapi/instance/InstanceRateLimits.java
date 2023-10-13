package com.fedilinks.fedilinksapi.instance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record InstanceRateLimits(
        @Value("${fedilinks.rate_limits.message:99}") Integer message,
        @Value("${fedilinks.rate_limits.message_per_second:999}") Integer messagePerSecond,
        @Value("${fedilinks.rate_limits.post:99}") Integer post,
        @Value("${fedilinks.rate_limits.post_per_second:999}") Integer postPerSecond,
        @Value("${fedilinks.rate_limits.register:99}") Integer register,
        @Value("${fedilinks.rate_limits.register_per_second:999}") Integer registerPerSecond,
        @Value("${fedilinks.rate_limits.image:99}") Integer image,
        @Value("${fedilinks.rate_limits.image_per_second:999}") Integer imagePerSecond,
        @Value("${fedilinks.rate_limits.comment:99}") Integer comment,
        @Value("${fedilinks.rate_limits.comment_per_second:999}") Integer commentPerSecond,
        @Value("${fedilinks.rate_limits.search:99}") Integer search,
        @Value("${fedilinks.rate_limits.search_per_second:999}") Integer searchPerSecond
) {
}
