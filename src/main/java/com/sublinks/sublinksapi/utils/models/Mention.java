package com.sublinks.sublinksapi.utils.models;

import lombok.Builder;

@Builder
public record Mention(
    String name,
    String domain
) {
}