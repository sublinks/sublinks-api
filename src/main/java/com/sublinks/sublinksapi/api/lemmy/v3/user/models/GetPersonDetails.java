package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.SortType;
import lombok.Builder;

@Builder
public record GetPersonDetails(
    Integer person_id,
    String username,
    SortType sort,
    Integer page,
    Integer limit,
    Integer community_id,
    Boolean saved_only
) {

}