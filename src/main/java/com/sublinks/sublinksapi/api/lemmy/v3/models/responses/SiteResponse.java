package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Tagline;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.SiteView;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record SiteResponse(
        SiteView site_view,
        @Singular List<Tagline> tag_lines
) {
}