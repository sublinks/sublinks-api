package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.DateSort;
import lombok.Builder;

@Builder
public record IndexPerson(String search,
                          boolean local,
                          DateSort dateSort,
                          int limit,
                          int page) {

}
