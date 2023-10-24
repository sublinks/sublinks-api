package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record ChangePassword(
        String new_password,
        String new_password_verify,
        String old_password
) {
}