package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

@Builder
public record Instance(
    Long id,
    String domain,
    String published,
    String updated,
    String software,
    String version
) {

}