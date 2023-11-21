package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import lombok.Builder;

@Builder
public record ModFeaturePost(
    Long id,
    Long mod_person_id,
    Long post_id,
    boolean featured,
    String when_,
    boolean is_featured_community
) {

}