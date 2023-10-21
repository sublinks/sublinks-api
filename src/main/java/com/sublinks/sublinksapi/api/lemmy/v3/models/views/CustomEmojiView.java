package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.CustomEmoji;
import lombok.Builder;

import java.util.List;

@Builder
public record CustomEmojiView(
        CustomEmoji custom_emoji,
        List<String> keywords
) {
}