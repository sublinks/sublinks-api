package com.sublinks.sublinksapi.api.sublinks.v1.roles.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortOrder;

public record IndexRole(
    String search,
    Integer page,
    Integer limit,
    SortOrder sort) {

}
