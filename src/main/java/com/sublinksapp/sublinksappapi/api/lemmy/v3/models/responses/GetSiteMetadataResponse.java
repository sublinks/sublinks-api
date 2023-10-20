package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.SiteMetadata;
import lombok.Builder;

@Builder
public record GetSiteMetadataResponse(
        SiteMetadata metadata
) {
}