package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * Represents a view of an administrative action to purge a community.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgeCommunityView(
    AdminPurgeCommunity admin_purge_community,
    Person admin
) {

}