package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CustomEmojiView(
    CustomEmoji custom_emoji,
    List<String> keywords
) {

}