package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortOrder;
import java.util.List;
import lombok.Builder;

@Builder
public record IndexPerson(String search,
                          boolean local,
                          SortOrder sortOrder,
                          int limit,
                          int page) {

}
