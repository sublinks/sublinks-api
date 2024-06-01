package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import lombok.Builder;

@Builder
public record IndexPerson(
    String search,
    SublinksListingType sublinksListingType,
    SortType sortType,
    Integer perPage,
    Integer page) {

}
