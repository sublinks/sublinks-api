package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import lombok.Builder;

@Builder
public record CustomEmojiResponse(
        CustomEmojiView custom_emoji
) {
}