package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import lombok.Builder;

@Builder
public record GetReplies(
        CommentSortType sort,
        Integer page,
        Integer limit,
        Boolean unread_only
) {
}