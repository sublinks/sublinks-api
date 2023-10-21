package com.sublinks.sublinksapi.api.lemmy.v3.models.requests;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.PostFeatureType;
import lombok.Builder;

@Builder
public record FeaturePost(
        Integer post_id,
        Boolean featured,
        PostFeatureType feature_type
) {
}