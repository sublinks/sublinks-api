package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record CommentReplyView(
        CommentReply comment_reply,
        Comment comment,
        Person creator,
        Post post,
        Community community,
        Person recipient,
        CommentAggregates counts,
        boolean creator_banned_from_community,
        boolean subscribed,
        boolean saved,
        boolean creator_blocked,
        int my_vote
) {
}