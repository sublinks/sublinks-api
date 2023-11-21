package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record BlockPerson(
    Integer person_id,
    Boolean block
) {

}