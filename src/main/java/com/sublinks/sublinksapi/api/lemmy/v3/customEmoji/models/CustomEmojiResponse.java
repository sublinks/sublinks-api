package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import lombok.Builder;

@Builder
public record CustomEmojiResponse(
        CustomEmojiView custom_emoji
) {
}