package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.AdminPurgeCommunity;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record AdminPurgeCommunityView(
        AdminPurgeCommunity admin_purge_community,
        Person admin
) {
}