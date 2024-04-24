package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import lombok.Builder;

@Builder
public record Langauges(String key,
                        String code,
                        String name) {

}
