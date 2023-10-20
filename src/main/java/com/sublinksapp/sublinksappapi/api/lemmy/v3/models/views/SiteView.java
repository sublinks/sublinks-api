package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.LocalSite;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.LocalSiteRateLimit;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Site;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.aggregates.SiteAggregates;
import lombok.Builder;

@Builder
public record SiteView(
        Site site,
        LocalSite local_site,
        LocalSiteRateLimit local_site_rate_limit,
        SiteAggregates counts
) {
}