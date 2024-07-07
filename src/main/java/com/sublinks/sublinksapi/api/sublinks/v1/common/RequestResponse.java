package com.sublinks.sublinksapi.api.sublinks.v1.common;

import lombok.Builder;

@Builder
public record RequestResponse(
    Boolean success,
    String error) {

}
