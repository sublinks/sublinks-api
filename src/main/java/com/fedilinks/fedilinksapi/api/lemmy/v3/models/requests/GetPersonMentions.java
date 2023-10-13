package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.CommentSortType;
import lombok.Builder;

@Builder
public record GetPersonMentions(
        CommentSortType sort,
        int page,
        int limit,
        boolean unread_only,
        String auth
) {
}