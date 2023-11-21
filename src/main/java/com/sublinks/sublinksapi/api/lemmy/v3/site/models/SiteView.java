package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record SiteView(
    Site site,
    LocalSite local_site,
    LocalSiteRateLimit local_site_rate_limit,
    SiteAggregates counts
) {

}