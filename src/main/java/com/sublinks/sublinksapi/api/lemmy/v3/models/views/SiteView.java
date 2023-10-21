package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalSite;
import com.sublinks.sublinksapi.api.lemmy.v3.models.LocalSiteRateLimit;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Site;
import com.sublinks.sublinksapi.api.lemmy.v3.models.aggregates.SiteAggregates;
import lombok.Builder;

@Builder
public record SiteView(
        Site site,
        LocalSite local_site,
        LocalSiteRateLimit local_site_rate_limit,
        SiteAggregates counts
) {
}