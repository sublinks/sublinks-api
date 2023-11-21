package com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record CustomEmojiResponse(
    CustomEmojiView custom_emoji
) {

}