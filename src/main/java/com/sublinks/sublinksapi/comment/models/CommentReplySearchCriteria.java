package com.sublinks.sublinksapi.comment.models;

import com.sublinks.sublinksapi.comment.enums.CommentReplySortType;
import com.sublinks.sublinksapi.community.dto.Community;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentReplySearchCriteria(

    CommentReplySortType sortType,
    int perPage,
    int page,
    boolean unreadOnly
) {

}
