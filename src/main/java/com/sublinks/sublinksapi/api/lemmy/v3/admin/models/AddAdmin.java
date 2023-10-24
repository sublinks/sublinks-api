package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import lombok.Builder;

@Builder
public record AddAdmin(
        Integer person_id,
        Boolean added
) {
}