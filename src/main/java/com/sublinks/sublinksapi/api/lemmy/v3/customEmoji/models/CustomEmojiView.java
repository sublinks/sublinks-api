package com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models;

import java.util.List;
import lombok.Builder;

@Builder
public record CustomEmojiView(
    CustomEmoji custom_emoji,
    List<String> keywords
) {

}