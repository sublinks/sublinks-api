package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.SiteMetadata;
import lombok.Builder;

@Builder
public record GetSiteMetadataResponse(
        SiteMetadata metadata
) {
}