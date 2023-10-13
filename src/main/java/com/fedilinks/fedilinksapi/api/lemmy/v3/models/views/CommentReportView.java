package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Comment;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.CommentReport;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.CommentAggregates;
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