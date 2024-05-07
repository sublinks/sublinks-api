package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

@Builder
public record IndexComment(String search,
                           SortType sortType,
                           SublinksListingType sublinksListingType,
                           Optional<List<String>> communityKeys,
                           Optional<List<String>> postKeys,
                           Optional<Boolean> showNsfw,
                           int limit,
                           int page) {

}
