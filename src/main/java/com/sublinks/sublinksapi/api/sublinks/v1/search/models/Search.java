package com.sublinks.sublinksapi.api.sublinks.v1.search.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import lombok.Builder;

@Builder
public record Search(
    String search,
    SortType type,
    SublinksListingType listingType,
    Boolean showNsfw,
    Boolean savedOnly,
    Integer perPage,
    Integer page) {

}
