package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchCriteria(
        SortType sortType,
        ListingType listingType,
        int perPage,
        int page,
        List<Long> communityIds,
        Person person,
        boolean isSavedOnly,
        boolean isDislikedOnly
) {
}
