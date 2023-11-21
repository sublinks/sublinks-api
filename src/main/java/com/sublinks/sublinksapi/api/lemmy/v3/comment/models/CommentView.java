package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
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