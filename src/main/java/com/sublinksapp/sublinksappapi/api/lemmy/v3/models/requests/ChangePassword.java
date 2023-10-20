package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.requests;

import lombok.Builder;

@Builder
public record ChangePassword(
        String new_password,
        String new_password_verify,
        String old_password,
        String auth
) {
}