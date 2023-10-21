package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.models.PostReport;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.PostAggregates;
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