package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record AdminPurgeCommunityView(
        AdminPurgeCommunity admin_purge_community,
        Person admin
) {
}