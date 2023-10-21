package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.SiteMetadata;
import lombok.Builder;

@Builder
public record GetSiteMetadataResponse(
        SiteMetadata metadata
) {
}