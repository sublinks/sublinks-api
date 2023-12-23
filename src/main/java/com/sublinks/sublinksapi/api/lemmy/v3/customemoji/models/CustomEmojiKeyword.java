package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CustomEmojiKeyword(
    Long custom_emoji_id,
    String keyword
) {

}