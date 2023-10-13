package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Community;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.PostReport;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.PostAggregates;
import lombok.Builder;

@Builder
public record PostReportView(
        PostReport post_report,
        Post post,
        Community community,
        Person creator,
        Person post_creator,
        boolean creator_banned_from_community,
        int my_vote,
        PostAggregates counts,
        Person resolver
) {
}