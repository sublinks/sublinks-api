package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SortType;
import com.sublinks.sublinksapi.api.sublinks.v1.common.enums.SublinksListingType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;

@Builder
public record IndexCommunity(String search,
                             @Schema(description = "Sort type", example = "Hot", requiredMode = RequiredMode.NOT_REQUIRED) SortType sortType,
                             @Schema(description = "Sublinks listing type", example = "All", requiredMode = RequiredMode.NOT_REQUIRED) SublinksListingType sublinksListingType,
                             @Schema(description = "Show NSFW", example = "false", requiredMode = RequiredMode.NOT_REQUIRED) Boolean showNsfw,
                             int limit,
                             int page) {

}
