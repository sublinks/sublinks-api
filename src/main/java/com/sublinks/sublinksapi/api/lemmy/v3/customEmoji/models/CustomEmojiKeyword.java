package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import lombok.Builder;

@Builder
public record CustomEmojiKeyword(
        Long id,
        Long custom_emoji_id,
        String keyword
) {
}