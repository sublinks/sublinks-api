package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.ListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;
import java.util.Optional;

@Builder
public record IndexCommunity(String search,
                             SortType sortType,
                             ListingType listingType,
                             Optional<List<String>> communityKeys,
                             Optional<Boolean> showNsfw,
                             int limit,
                             int page) {

}
