package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record CommentReportView(
        CommentReport comment_report,
        Comment comment,
        Post post,
        Community community,
        Person creator,
        Person comment_creator,
        CommentAggregates counts,
        boolean creator_banned_from_community,
        int my_vote,
        Person resolver
) {
}