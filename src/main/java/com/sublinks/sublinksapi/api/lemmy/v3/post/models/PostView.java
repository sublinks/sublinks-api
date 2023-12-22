package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PostView(
    Post post,
    Person creator,
    Community community,
    boolean creator_banned_from_community,
    boolean creator_is_moderator,
    boolean creator_is_admin,
    PostAggregates counts,
    SubscribedType subscribed,
    boolean saved,
    boolean read,
    boolean creator_blocked,
    int my_vote,
    int unread_comments
) {

}