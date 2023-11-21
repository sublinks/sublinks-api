package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import lombok.Builder;

@Builder
public record DeleteCustomEmojiResponse(
    int id,
    boolean success
) {

}