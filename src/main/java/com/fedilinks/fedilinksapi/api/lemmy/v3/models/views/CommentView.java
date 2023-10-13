package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.SubscribedType;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Comment;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.CommentAggregates;
import lombok.Builder;

@Builder
public record CommentView(
        Comment comment,
        Person creator,
        Post post,
        Community community,
        CommentAggregates counts,
        boolean creator_banned_from_community,
        SubscribedType subscribed,
        boolean saved,
        boolean creator_blocked,
        int my_vote
) {
}