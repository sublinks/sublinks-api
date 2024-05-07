package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import lombok.Builder;
import java.util.List;
import java.util.Optional;

@Builder
public record IndexCommunity(String search,
                             SortType sortType,
                             SublinksListingType sublinksListingType,
                             Boolean showNsfw,
                             int limit,
                             int page) {

}
