package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import lombok.Builder;
import java.util.Optional;

@Builder
@SuppressWarnings("RecordComponentName")
public record CreatePost(
    String name,
    Integer community_id,
    Optional<String> url,
    String body,
    String honeypot,
    Boolean nsfw,
    Integer language_id
) {

}