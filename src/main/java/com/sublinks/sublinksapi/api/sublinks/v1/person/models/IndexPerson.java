package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import lombok.Builder;

@Builder
public record IndexPerson(String search,
                          SublinksListingType sublinksListingType,
                          SortType sortType,
                          int limit,
                          int page) {

}
