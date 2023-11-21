package com.sublinks.sublinksapi.api.lemmy.v3.resolveObject.models;

import lombok.Builder;

@Builder
public record ResolveObject(
    String q
) {

}