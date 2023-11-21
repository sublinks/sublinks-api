package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import java.util.List;
import lombok.Builder;

@Builder
public record SiteResponse(
    SiteView site_view,
    List<Tagline> tag_lines
) {

}