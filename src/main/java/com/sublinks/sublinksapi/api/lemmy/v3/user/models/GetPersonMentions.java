package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import lombok.Builder;

import java.util.Optional;

@Builder
public record GetPersonMentions(
        CommentSortType sort,
        Integer page,
        Integer limit,
        Optional<Boolean> unread_only
) {
}