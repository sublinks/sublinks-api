package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

import java.util.List;

@Builder
public record SiteResponse(
        SiteView site_view,
        List<Tagline> tag_lines
) {
}