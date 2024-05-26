package com.sublinks.sublinksapi.api.sublinks.v1.languages.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LanguageResponse(String key,
                               String code,
                               String name) {

}
