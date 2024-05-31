package com.sublinks.sublinksapi.api.sublinks.v1.search.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import lombok.Builder;

// @todo: Add Communities, Posts, Comments, and Messages
@Builder
public record Search(
    String search,
    SortType type,
    SublinksListingType sublinksListingType,
    Boolean showNsfw,
    Integer perPage,
    Integer page) {

}
