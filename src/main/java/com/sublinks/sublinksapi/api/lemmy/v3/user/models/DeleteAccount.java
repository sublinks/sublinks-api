package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record DeleteAccount(
    String password,
    Boolean delete_content
) {

}