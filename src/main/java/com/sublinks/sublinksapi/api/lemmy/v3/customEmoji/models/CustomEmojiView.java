package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import lombok.Builder;

import java.util.List;

@Builder
public record CustomEmojiView(
        CustomEmoji custom_emoji,
        List<String> keywords
) {
}