package com.sublinks.sublinksapi.post.models;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import java.util.List;
import com.sublinks.sublinksapi.utils.CursorBasedPageable;
import lombok.Builder;

@Builder
public record PostSearchCriteria(
    SortType sortType,
    ListingType listingType,
    int perPage,
    int page,
    CursorBasedPageable cursorBasedPageable,
    List<Long> communityIds,
    Person person,
    boolean isSavedOnly,
    boolean isLikedOnly,
    boolean isDislikedOnly
) {

}
