package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record CommunityView(CommunityResponse community,
                            CommunityAggregatesResponse communityAggregates,
                            List<PersonResponse> moderators) {

}
