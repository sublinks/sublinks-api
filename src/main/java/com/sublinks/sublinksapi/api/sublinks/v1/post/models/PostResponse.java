package com.sublinks.sublinksapi.api.sublinks.v1.post.models;

import com.sublinks.sublinksapi.api.sublinks.v1.community.models.CommunityResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;

public record PostResponse(String key,
                           String title,
                           String titleSlug,
                           String body,
                           LinkMetaData linkMetaData,
                           String type,
                           Boolean isLocal,
                           Boolean isDeleted,
                           Boolean isRemoved,
                           Boolean isNsfw,
                           Boolean isLocked,
                           Boolean isFeatured,
                           Boolean isFeaturedInCommunity,
                           CommunityResponse community,
                           PersonResponse creator,
                           PostAggregateResponse postAggregate,
                           String activityPubId,
                           String publicKey,
                           String createdAt,
                           String updatedAt) {

}
