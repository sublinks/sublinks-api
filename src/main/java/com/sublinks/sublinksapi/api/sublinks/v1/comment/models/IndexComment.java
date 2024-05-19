package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import lombok.Builder;

@Builder
public record IndexComment(String search,
                           SortType sortType,
                           SublinksListingType sublinksListingType,
                           String communityKey,
                           String postKey,
                           Boolean showNsfw,
                           Boolean savedOnly,
                           Integer limit,
                           Integer page) {

}
