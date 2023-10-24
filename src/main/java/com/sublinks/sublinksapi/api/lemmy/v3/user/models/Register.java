package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record Register(
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotEmpty
        String password_verify,
        Boolean show_nsfw,
        @Email
        String email,
        String captcha_answer,
        String honeypot,
        String answer
) {
}