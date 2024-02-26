package com.sublinks.sublinksapi.comment.models;

import com.sublinks.sublinksapi.comment.enums.CommentReplySortType;
import lombok.Builder;

@Builder
public record CommentLikeSearchCriteria(
    Long commentId,
    int perPage,
    int page
) {

}
