package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
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