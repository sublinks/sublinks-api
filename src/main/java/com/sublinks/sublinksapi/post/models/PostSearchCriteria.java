package com.sublinks.sublinksapi.post.models;

import com.sublinks.sublinksapi.person.dto.Person;

import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import java.util.List;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.utils.CursorBasedPageable;
import lombok.Builder;

@Builder
public record PostSearchCriteria(
    SortType sortType,
    ListingType listingType,
    int perPage,
    int page,
    CursorBasedPageable<Post> cursorBasedPageable,
    List<Long> communityIds,
    Person person,
    boolean isSavedOnly,
    boolean isLikedOnly,
    boolean isDislikedOnly
) {

}
