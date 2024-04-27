package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.comment.enums.CommentReplySortType;
import java.util.Optional;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetReplies(
    CommentReplySortType sort,
    Integer page,
    Integer limit,
    Optional<Boolean> unread_only
) {

}