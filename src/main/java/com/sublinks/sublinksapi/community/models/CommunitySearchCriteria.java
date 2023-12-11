package com.sublinks.sublinksapi.community.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ListingType;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import com.sublinks.sublinksapi.person.dto.Person;
import lombok.Builder;

@Builder
public record CommunitySearchCriteria(
    SortType sortType,
    ListingType listingType,
    int perPage,
    int page,
    boolean showNsfw,
    Person person
) {

}
