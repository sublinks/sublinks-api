package com.sublinks.sublinksapi.person.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import lombok.Builder;

@Builder
public record PersonMentionSearchCriteria(
    CommentSortType sort,
    int page,
    int perPage,
    Boolean unreadOnly
) {

}
