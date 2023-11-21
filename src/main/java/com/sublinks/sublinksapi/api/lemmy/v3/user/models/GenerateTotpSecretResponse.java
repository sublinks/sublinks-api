package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record GenerateTotpSecretResponse(
    String totp_secret_url
) {

}
