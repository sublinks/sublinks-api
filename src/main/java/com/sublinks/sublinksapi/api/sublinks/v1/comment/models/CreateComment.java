package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateComment(String body,
                            String parentKey,
                            String postKey,
                            Optional<String> languageKey,
                            Optional<Boolean> featured) {

}
