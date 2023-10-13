package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import lombok.Builder;

@Builder
public record GenerateTotpSecretResponse(
        String totp_secret_url
) {
}
