package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record UpdateTotp(
    boolean enabled,
    String totp_token
) {

}
