package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import java.util.Optional;
import lombok.Builder;

@Builder
public record GetPersonMentions(
    CommentSortType sort,
    Integer page,
    Integer limit,
    Optional<Boolean> unread_only
) {

}