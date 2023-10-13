package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import com.fedilinks.fedilinksapi.api.lemmy.v3.enums.PostFeatureType;
import lombok.Builder;

@Builder
public record FeaturePost(
        int post_id,
        boolean featured,
        PostFeatureType feature_type,
        String auth
) {
}