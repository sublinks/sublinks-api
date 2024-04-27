package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * Represents a view of an administrative action to purge a post.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgePostView(
    AdminPurgePost admin_purge_post,
    Person admin,
    Community community
) {

}