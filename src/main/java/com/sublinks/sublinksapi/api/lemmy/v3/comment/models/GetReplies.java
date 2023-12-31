package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.enums.CommentReplySortType;
import lombok.Builder;
import java.util.Optional;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetReplies(
    CommentReplySortType sort,
    Integer page,
    Integer limit,
    Boolean unread_only
) {

}