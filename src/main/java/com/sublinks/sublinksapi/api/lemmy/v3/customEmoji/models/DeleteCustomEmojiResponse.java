package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import lombok.Builder;

@Builder
public record DeleteCustomEmojiResponse(
        int id,
        boolean success
) {
}