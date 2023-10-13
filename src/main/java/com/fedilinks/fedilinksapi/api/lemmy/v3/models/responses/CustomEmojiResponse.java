package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import lombok.Builder;

@Builder
public record CustomEmojiResponse(
        CustomEmojiView custom_emoji
) {
}