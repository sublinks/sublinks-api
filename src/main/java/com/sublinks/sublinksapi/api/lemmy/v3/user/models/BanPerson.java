package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
public record BanPerson(
    Integer person_id,
    String reason,
    Boolean ban,
    Boolean remove_data,
    Long expires

) {

}