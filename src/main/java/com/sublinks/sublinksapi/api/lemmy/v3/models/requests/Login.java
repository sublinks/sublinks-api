package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Login(
        @NotNull
        String username_or_email,
        @NotBlank
        String password,
        String totp_2fa_token
) {
}