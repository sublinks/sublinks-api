package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

@Builder
public record GetCommunity(
    Long id,
    String name
) {

}