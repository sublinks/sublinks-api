package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.AdminPurgeCommunity;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record AdminPurgeCommunityView(
        AdminPurgeCommunity admin_purge_community,
        Person admin
) {
}